# Evil Permit

App Android privacy-first: tự thu hồi quyền thừa của app khác, chặn truy cập thông tin chuyên sâu (AppOps), rollback một chạm. Backend đặc quyền: Shizuku (mặc định), Device Owner, Root/LSPosed (tùy chọn).

Spec đầy đủ: doc "Evil Permit – Blueprint" (https://claude.ai/artifact/Nw6amwhuH5Vx7DC4gBibgY), mục 1–10.
Thiết kế UI: canvas "Evil Permit App Design" (https://claude.ai/artifact/DjUASpCp83ZqKvD3w544TJ), pattern One UI.

## Cách làm việc

- Mỗi phase một branch `phase-N`, một PR vào `main`. Không push thẳng `main`.
- Xong phase khi CI xanh (`./gradlew assembleDebug test`) và đủ acceptance criteria của phase.
- Trong PR: liệt kê tiêu chí nào cần test trên máy thật (Shizuku, AppOps, VoLTE) kèm lệnh adb để test.
- Commit theo Conventional Commits: `feat(policy): ...`, `fix(shizuku): ...`, `chore(build): ...`.
- Môi trường cloud của agent không tải được Maven/Google: build được kiểm chứng qua GitHub Actions, không chạy Gradle cục bộ.

## Stack

- Kotlin 2.1, Coroutines + Flow, Jetpack Compose + Material 3, Hilt (KSP), Room (KSP), WorkManager.
- Gradle KTS, version catalog `gradle/libs.versions.toml`, convention plugins trong `build-logic/`.
- minSdk 29, target/compileSdk 35, JVM 17. Flavor `legacy` (minSdk 21) chỉ thêm ở Phase 8.
- Test: module JVM dùng JUnit 5; module Android dùng JUnit 4 + Robolectric (JUnit 5 trên Android cần plugin riêng, chưa dùng). Turbine cho Flow, MockK.

## Module

```text
app/                 Compose UI, Navigation, Hilt entry
core/model/          data class thuần (JVM)
core/scanner/        đọc PackageInfo, permissions, <queries>
core/policy/         rule engine thuần (JVM), PolicyEngine.evaluate() là pure function
core/enforcer-api/   interface Enforcer + Capability + EnforceResult (JVM)
core/monitor/        PACKAGE_ADDED, UidObserver, AppOps history
core/data/           Room, repositories
backend/shizuku/     UserService AIDL ở shell uid
backend/owner/       DeviceAdminReceiver + DPM
backend/xposed/      LSPosed module, APK riêng (thêm ở Phase 6)
```

Quy tắc phụ thuộc: `app` → `core/*` + `backend/*`. `backend/*` chỉ phụ thuộc `core:enforcer-api` và `core:model`. `core:policy` không biết backend nào.

Convention plugin: `evilpermit.android.application`, `evilpermit.android.library`, `evilpermit.android.compose`, `evilpermit.android.hilt`, `evilpermit.android.room`, `evilpermit.jvm.library`.

## Quy ước code

- Package gốc `vn.gemssoftware.evilpermit`.
- Không `!!`, không `GlobalScope`. Lệnh gọi enforcer chạy trên `Dispatchers.IO`, trả `EnforceResult` thay vì throw.
- AppOps ép về `IGNORE` (trả dữ liệu rỗng), không dùng `DENY` trừ khi có lý do.
- Không bao giờ thu hồi quyền của allowlist hệ thống (`android`, `com.android.systemui`, `com.android.phone`, launcher và IME mặc định, chính app, Shizuku). Luôn lưu Snapshot trước khi đổi.
- Manifest bản chính không có `android.permission.INTERNET`. CI fail nếu merged manifest chứa quyền này. Tính năng mạng (VpnService, DoH, vượt DPI) nằm trong module/flavor riêng.
- Màu và motion lấy từ token trong `app/.../ui/theme` (easing One UI `cubic-bezier(0.22, 0.25, 0, 1)`, 100–500 ms). Text phải đạt tương phản 4.5:1, có unit test kiểm tra.
- Comment và log bằng tiếng Anh. Chuỗi UI qua `strings.xml`, mặc định tiếng Anh, `values-vi` tiếng Việt.

## Roadmap

| Phase | Nội dung | Acceptance |
| --- | --- | --- |
| 0 | Khung dự án, CI | `assembleDebug` pass, CI xanh |
| 1 | Scanner + Policy | ≥30 case test `evaluate()`, coverage policy ≥90%, màn debug dry-run |
| 2 | Backend Shizuku | Android 13/14 máy thật: revoke `READ_CONTACTS` app không xin lại được; `READ_CLIPBOARD` IGNORE trả chuỗi rỗng |
| 3 | Room + auto-deny + rollback | APK test 6 quyền bị chặn đúng ma trận trong 5 giây; hoàn tác về đúng trạng thái |
| 4 | UI đầy đủ + cấp tạm thời + drift | Cấp tạm Camera, về Home 30 giây thì bị thu hồi |
| 5 | Backend Device Owner | Revoke được khi không có Shizuku |
| 6 | Module LSPosed | `getInstalledPackages` không thấy package bị ẩn |
| 7 | Hoàn thiện | Export/import JSON, i18n, R8, release ký bằng keystore từ GitHub Secrets |
| 8 | Telephony: VoLTE + VoL Caller | Checker báo đúng; bật VoLTE hiện menu hệ thống; VoL Caller gọi và hiện HD |

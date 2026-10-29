plugins {
    alias(libs.plugins.evilpermit.android.library)
}

android {
    namespace = "vn.gemssoftware.evilpermit.backend.shizuku"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.enforcerApi)
}

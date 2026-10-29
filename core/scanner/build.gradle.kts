plugins {
    alias(libs.plugins.evilpermit.android.library)
}

android {
    namespace = "vn.gemssoftware.evilpermit.core.scanner"
}

dependencies {
    implementation(projects.core.model)
}

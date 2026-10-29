plugins {
    alias(libs.plugins.evilpermit.android.library)
    alias(libs.plugins.evilpermit.android.room)
}

android {
    namespace = "vn.gemssoftware.evilpermit.core.data"
}

dependencies {
    implementation(projects.core.model)
}

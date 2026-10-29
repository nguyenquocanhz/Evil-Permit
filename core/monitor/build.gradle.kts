plugins {
    alias(libs.plugins.evilpermit.android.library)
}

android {
    namespace = "vn.gemssoftware.evilpermit.core.monitor"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.enforcerApi)
}

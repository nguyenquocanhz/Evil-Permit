plugins {
    alias(libs.plugins.evilpermit.android.application)
    alias(libs.plugins.evilpermit.android.compose)
    alias(libs.plugins.evilpermit.android.hilt)
}

android {
    namespace = "vn.gemssoftware.evilpermit"

    defaultConfig {
        applicationId = "vn.gemssoftware.evilpermit"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.scanner)
    implementation(projects.core.policy)
    implementation(projects.core.enforcerApi)
    implementation(projects.core.monitor)
    implementation(projects.core.data)
    implementation(projects.backend.shizuku)
    implementation(projects.backend.owner)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
}

plugins {
    alias(libs.plugins.evilpermit.jvm.library)
}

dependencies {
    api(projects.core.model)
    api(libs.kotlinx.coroutines.core)
}

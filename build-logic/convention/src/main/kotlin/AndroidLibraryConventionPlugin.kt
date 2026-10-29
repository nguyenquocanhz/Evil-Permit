import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import vn.gemssoftware.evilpermit.buildlogic.ProjectConfig
import vn.gemssoftware.evilpermit.buildlogic.configureKotlinAndroid
import vn.gemssoftware.evilpermit.buildlogic.lib
import vn.gemssoftware.evilpermit.buildlogic.libs

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                testOptions.targetSdk = ProjectConfig.TARGET_SDK
                testOptions.unitTests.isReturnDefaultValues = true
            }

            dependencies {
                add("testImplementation", libs.lib("junit4"))
            }
        }
    }
}

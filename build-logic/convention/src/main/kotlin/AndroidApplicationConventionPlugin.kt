import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import vn.gemssoftware.evilpermit.buildlogic.ProjectConfig
import vn.gemssoftware.evilpermit.buildlogic.configureKotlinAndroid
import vn.gemssoftware.evilpermit.buildlogic.lib
import vn.gemssoftware.evilpermit.buildlogic.libs

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = ProjectConfig.TARGET_SDK
                testOptions.unitTests.isReturnDefaultValues = true
            }

            dependencies {
                add("testImplementation", libs.lib("junit4"))
            }
        }
    }
}

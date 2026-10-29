import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import vn.gemssoftware.evilpermit.buildlogic.configureKotlinJvm
import vn.gemssoftware.evilpermit.buildlogic.lib
import vn.gemssoftware.evilpermit.buildlogic.libs

/** Pure Kotlin module (no Android). Tests run on JUnit 5. */
class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            configureKotlinJvm()

            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }

            dependencies {
                add("testImplementation", platform(libs.lib("junit-bom")))
                add("testImplementation", libs.lib("junit-jupiter"))
                add("testRuntimeOnly", libs.lib("junit-platform-launcher"))
            }
        }
    }
}

import com.android.build.gradle.LibraryExtension
import fit.asta.health.apps.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidTestDataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                testOptions {
                    unitTests {
                        this.all {
                            it.useJUnitPlatform()
                        }
                        isIncludeAndroidResources = true
                    }
                }
            }

            dependencies {
                "implementation"(libs.findLibrary("mockk.android").get())
                "implementation"(libs.findLibrary("turbine").get())
                "implementation"(libs.findLibrary("kotlinx.coroutines.test").get())
                "implementation"(libs.findLibrary("junit.jupiter").get())
                "implementation"(libs.findLibrary("okhttp.mockwebserver").get())
            }
        }
    }
}

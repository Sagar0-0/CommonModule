package fit.asta.health.apps

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import java.util.Properties

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidGoogleMaps(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    with(pluginManager) {
        apply("com.google.gms.google-services")
        apply("com.google.android.gms.oss-licenses-plugin")
        apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    }

    commonExtension.apply {

        val secretProps = Properties().apply {
            load(rootProject.file("secrets.debug.properties").inputStream())
        }

        buildTypes {
            getByName("debug") {
                resValue("string", "MAPS_API_KEY", secretProps["MAPS_API_KEY"].toString())
            }
            getByName("release") {
                resValue("string", "MAPS_API_KEY", secretProps["MAPS_API_KEY"].toString())
            }
        }
    }

    dependencies {
        "implementation"(libs.findLibrary("maps.compose").get())
        "implementation"(libs.findLibrary("play.services.maps").get())
        "implementation"(libs.findLibrary("play.services.location").get())
        "implementation"(libs.findLibrary("places").get())
    }
}
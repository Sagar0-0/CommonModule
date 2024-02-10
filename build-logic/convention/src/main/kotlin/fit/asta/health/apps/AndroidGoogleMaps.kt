package fit.asta.health.apps

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidGoogleMaps() {

    with(pluginManager) {
        apply("com.google.gms.google-services")
        apply("com.google.android.gms.oss-licenses-plugin")
        apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    }

    dependencies {
        "implementation"(libs.findLibrary("maps.compose").get())
        "implementation"(libs.findLibrary("play.services.maps").get())
        "implementation"(libs.findLibrary("play.services.location").get())
        "implementation"(libs.findLibrary("places").get())
    }
}
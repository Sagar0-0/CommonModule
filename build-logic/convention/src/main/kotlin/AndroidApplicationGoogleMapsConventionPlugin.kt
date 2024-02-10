
import fit.asta.health.apps.configureAndroidGoogleMaps
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationGoogleMapsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureAndroidGoogleMaps()
        }
    }
}
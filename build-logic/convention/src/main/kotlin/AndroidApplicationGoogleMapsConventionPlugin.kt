import com.android.build.api.dsl.ApplicationExtension
import fit.asta.health.apps.configureAndroidGoogleMaps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationGoogleMapsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidGoogleMaps(extension)
        }
    }
}
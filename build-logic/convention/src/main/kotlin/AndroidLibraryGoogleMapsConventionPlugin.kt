import com.android.build.gradle.LibraryExtension
import fit.asta.health.apps.configureAndroidGoogleMaps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryGoogleMapsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidGoogleMaps(extension)
        }
    }
}
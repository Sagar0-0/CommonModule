package fit.asta.health.imageCropper.relatedFiles

import android.net.Uri
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import java.net.URLEncoder

val LocalNavigator = compositionLocalOf<Navigator> {
    error("Navigator not found")
}

class Navigator(private val navController: NavHostController) {
    fun navigateToFile(uri: Uri) {
        navController.navigate(
            Screen.File.path.replace(
                "{${Screen.File.argUri}}", URLEncoder.encode(uri.toString())
            )
        )
    }
}
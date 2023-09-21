package fit.asta.health.designsystemx.molecular.background

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystemx.AstaThemeX

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AstaThemeX {
        AstaBackground {}
    }
}


/**
 * The main background for the app.
 * Uses [AstaThemeX.colorsX] surface color to set the color.
 * Tonal Elevation of the Surface is set to 2.dp
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun AstaBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = AstaThemeX.colorsX.surface,
        tonalElevation = 2.dp
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

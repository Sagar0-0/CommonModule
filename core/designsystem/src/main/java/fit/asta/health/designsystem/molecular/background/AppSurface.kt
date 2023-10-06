package fit.asta.health.designsystem.molecular.background

import android.content.res.Configuration
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppSurface {}
}


/**
 * The surface for the app. This serves as a base composable function for all the screens
 * Uses [AppTheme.colors] surface color to set the color.
 * Tonal Elevation of the Surface is set to 2.dp
 *
 * @param modifier Modifier to be applied to the background.
 * @param color Color to be applied to the Surface.
 * @param content The background content.
 */
@Composable
fun AppSurface(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.surface,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = color
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}
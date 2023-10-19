package fit.asta.health.designsystem.molecular.background

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppSurface {}
}


/**
 * Material surface is the central metaphor in material design. Each surface exists at a given
 * elevation, which influences how that piece of surface visually relates to other surfaces and how
 * that surface is modified by tonal variance.
 *
 * The Surface is responsible for:
 * 1) Clipping: Surface clips its children to the shape specified by [shape]
 * 2) Borders: If [shape] has a border, then it will also be drawn.
 * 3) Background: Surface fills the shape specified by [shape] with the [color]. If [color] is
 * [ColorScheme.surface] a color overlay will be applied. The color of the overlay depends on the
 * [tonalElevation] of this Surface, and the [LocalAbsoluteTonalElevation] set by any parent
 * surfaces. This ensures that a Surface never appears to have a lower elevation overlay than its
 * ancestors, by summing the elevation of all previous Surfaces.
 * 4) Content color: Surface uses [contentColor] to specify a preferred color for the content of
 * this surface - this is used by the [Text] and [Icon] components as a default color.
 * 5) Blocking touch propagation behind the surface.
 *
 * @param modifier Modifier to be applied to the layout corresponding to the surface
 * @param shape Defines the surface's shape as well its shadow.
 * @param color The background color. Use [Color.Transparent] to have no color.
 * @param contentColor The preferred content color provided by this Surface to its children.
 * Defaults to either the matching content color for [color], or if [color] is not a color from the
 * theme, this will keep the same value set above this Surface.
 * @param tonalElevation When [color] is [ColorScheme.surface], a higher the elevation will result
 * in a darker color in light theme and lighter color in dark theme.
 * @param shadowElevation The size of the shadow below the surface. To prevent shadow creep, only
 * apply shadow elevation when absolutely necessary, such as when the surface requires visual
 * separation from a patterned background. Note that It will not affect z index of the Surface.
 * If you want to change the drawing order you can use `Modifier.zIndex`.
 * @param border Optional border to draw on top of the surface
 * @param content This is the composable body for the Surface
 */
@Composable
fun AppSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colors.surface,
    contentColor: Color = AppTheme.colors.onSurface,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = shape,
        border = border,
        shadowElevation = shadowElevation,
        tonalElevation = tonalElevation,
        content = content,
        contentColor = contentColor
    )
}
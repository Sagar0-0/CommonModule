package fit.asta.health.designsystem.molecular.background

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme


// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            Column {
                AppNavigationBar {}
            }
        }
    }
}


/**
 * Navigation bars offer a persistent and convenient way to switch between primary destinations in
 * an app.
 *
 * ![Navigation bar image](https://developer.android.com/images/reference/androidx/compose/material3/navigation-bar.png)
 *
 * [AppNavigationBar] should contain three to five [NavigationBarItem]s, each representing a singular
 * destination.
 *
 * @param modifier the [Modifier] to be applied to this navigation bar
 * @param containerColor the color used for the background of this navigation bar. Use
 * [Color.Transparent] to have no color.
 * @param contentColor the preferred color for content inside this navigation bar. Defaults to
 * either the matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param tonalElevation when [containerColor] is [ColorScheme.surface], a translucent primary color
 * overlay is applied on top of the container. A higher tonal elevation value will result in a
 * darker color in light theme and lighter color in dark theme. See also: [Surface].
 * @param windowInsets a window insets of the navigation bar.
 * @param content the content of this navigation bar, typically 3-5 [NavigationBarItem]s
 */
@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.colors.surface,
    contentColor: Color = AppTheme.colors.onSurface,
    tonalElevation: Dp = AppTheme.elevation.level2,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        windowInsets = windowInsets,
        content = content
    )
}
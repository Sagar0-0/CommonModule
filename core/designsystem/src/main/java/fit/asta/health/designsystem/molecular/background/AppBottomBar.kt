package fit.asta.health.designsystem.molecular.background

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme


/**
 * A bottom app bar displays navigation and key actions at the bottom of mobile screens.
 *
 * ![Bottom app bar image](https://developer.android.com/images/reference/androidx/compose/material3/bottom-app-bar.png)
 *
 * @param actions the icon content of this BottomAppBar. The default layout here is a [Row],
 * so content inside will be placed horizontally.
 * @param modifier the [Modifier] to be applied to this BottomAppBar
 * @param floatingActionButton optional floating action button at the end of this BottomAppBar
 * @param containerColor the color used for the background of this BottomAppBar. Use
 * [Color.Transparent] to have no color.
 * @param contentColor the preferred color for content inside this BottomAppBar. Defaults to either
 * the matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param tonalElevation when [containerColor] is [ColorScheme.surface], a translucent primary color
 * overlay is applied on top of the container. A higher tonal elevation value will result in a
 * darker color in light theme and lighter color in dark theme. See also: [Surface].
 * @param contentPadding the padding applied to the content of this BottomAppBar
 * @param windowInsets a window insets that app bar will respect.
 */
@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit,
    containerColor: Color = AppTheme.colors.surface,
    contentColor: Color = AppTheme.colors.onSurface,
    tonalElevation: Dp = AppTheme.elevation.level1,
    contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
    windowInsets: WindowInsets = BottomAppBarDefaults.windowInsets,
    actions: @Composable() (RowScope.() -> Unit)
) {
    BottomAppBar(
        modifier = modifier,
        floatingActionButton = floatingActionButton,
        actions = actions,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        contentPadding = contentPadding,
        windowInsets = windowInsets,
        containerColor = containerColor
    )
}
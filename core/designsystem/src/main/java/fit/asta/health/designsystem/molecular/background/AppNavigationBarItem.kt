package fit.asta.health.designsystem.molecular.background

import android.content.res.Configuration
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

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
                AppNavigationBar(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = AppTheme.elevation.level4
                ) {

                    for (i in 1..3) {
                        AppNavigationBarItem(
                            icon = {
                                AppIcon(imageVector = Icons.Default.ArtTrack)
                            },
                            label = "test",
                            selected = true,
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}


/**
 * Navigation bars offer a persistent and convenient way to switch between primary destinations in
 * an app.
 *
 * The recommended configuration for a [AppNavigationBarItem] depends on how many items there are
 * inside a [AppNavigationBar]:
 *
 * - Three destinations: Display icons and text labels for all destinations.
 * - Four destinations: Active destinations display an icon and text label. Inactive destinations
 * display icons, and text labels are recommended.
 * - Five destinations: Active destinations display an icon and text label. Inactive destinations
 * use icons, and use text labels if space permits.
 *
 * A [NavigationBarItem] always shows text labels (if it exists) when selected. Showing text
 * labels if not selected is controlled by [alwaysShowLabel].
 *
 * @param selected whether this item is selected
 * @param onClick called when this item is clicked
 * @param icon icon for this item, typically an [Icon]
 * @param modifier the [Modifier] to be applied to this item
 * @param enabled controls the enabled state of this item. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label optional text label for this item
 * @param alwaysShowLabel whether to always show the label for this item. If `false`, the label will
 * only be shown when this item is selected.
 * @param colors [NavigationBarItemColors] that will be used to resolve the colors used for this
 * item in different states. See [NavigationBarItemDefaults.colors].
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this item. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this item in different states.
 */
@Composable
fun RowScope.AppNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = AppTheme.colors.onSecondaryContainer,
        selectedTextColor = AppTheme.colors.onSurface,
        indicatorColor = AppTheme.colors.secondaryContainer,
        unselectedIconColor = AppTheme.colors.onSurfaceVariant,
        unselectedTextColor = AppTheme.colors.onSurfaceVariant,
        disabledIconColor = AppTheme.colors.onSurfaceVariant.copy(AppTheme.alphaValues.level2),
        disabledTextColor = AppTheme.colors.onSurfaceVariant.copy(AppTheme.alphaValues.level2)
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = {
            if (label != null) {
                CaptionTexts.Level2(text = label)
            }
        },
        alwaysShowLabel = alwaysShowLabel,
        colors = colors,
        interactionSource = interactionSource
    )
}


/**
 * Navigation bars offer a persistent and convenient way to switch between primary destinations in
 * an app.
 *
 * The recommended configuration for a [AppNavigationBarItem] depends on how many items there are
 * inside a [AppNavigationBar]:
 *
 * - Three destinations: Display icons and text labels for all destinations.
 * - Four destinations: Active destinations display an icon and text label. Inactive destinations
 * display icons, and text labels are recommended.
 * - Five destinations: Active destinations display an icon and text label. Inactive destinations
 * use icons, and use text labels if space permits.
 *
 * A [NavigationBarItem] always shows text labels (if it exists) when selected. Showing text
 * labels if not selected is controlled by [alwaysShowLabel].
 *
 * @param selected whether this item is selected
 * @param onClick called when this item is clicked
 * @param icon icon for this item, typically an [Icon]
 * @param contentDescription This is the content description for the Icon
 * @param modifier the [Modifier] to be applied to this item
 * @param enabled controls the enabled state of this item. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label optional text label for this item
 * @param alwaysShowLabel whether to always show the label for this item. If `false`, the label will
 * only be shown when this item is selected.
 * @param colors [NavigationBarItemColors] that will be used to resolve the colors used for this
 * item in different states. See [NavigationBarItemDefaults.colors].
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this item. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this item in different states.
 */
@Composable
fun RowScope.AppNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String? = null,
    enabled: Boolean = true,
    label: String? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            AppIcon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        },
        modifier = modifier,
        enabled = enabled,
        label = {
            if (label != null) {
                CaptionTexts.Level2(text = label)
            }
        },
        alwaysShowLabel = alwaysShowLabel,
        colors = colors,
        interactionSource = interactionSource
    )
}


/**
 * Navigation bars offer a persistent and convenient way to switch between primary destinations in
 * an app.
 *
 * The recommended configuration for a [AppNavigationBarItem] depends on how many items there are
 * inside a [AppNavigationBar]:
 *
 * - Three destinations: Display icons and text labels for all destinations.
 * - Four destinations: Active destinations display an icon and text label. Inactive destinations
 * display icons, and text labels are recommended.
 * - Five destinations: Active destinations display an icon and text label. Inactive destinations
 * use icons, and use text labels if space permits.
 *
 * A [NavigationBarItem] always shows text labels (if it exists) when selected. Showing text
 * labels if not selected is controlled by [alwaysShowLabel].
 *
 * @param selected whether this item is selected
 * @param onClick called when this item is clicked
 * @param icon icon for this item, typically an [Icon]
 * @param contentDescription This is the content description for the Icon
 * @param modifier the [Modifier] to be applied to this item
 * @param enabled controls the enabled state of this item. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label optional text label for this item
 * @param alwaysShowLabel whether to always show the label for this item. If `false`, the label will
 * only be shown when this item is selected.
 * @param colors [NavigationBarItemColors] that will be used to resolve the colors used for this
 * item in different states. See [NavigationBarItemDefaults.colors].
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this item. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this item in different states.
 */
@Composable
fun RowScope.AppNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    contentDescription: String? = null,
    enabled: Boolean = true,
    label: String? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            AppIcon(
                painter = icon,
                contentDescription = contentDescription
            )
        },
        modifier = modifier,
        enabled = enabled,
        label = {
            if (label != null) {
                CaptionTexts.Level5(text = label)
            }
        },
        alwaysShowLabel = alwaysShowLabel,
        colors = colors,
        interactionSource = interactionSource
    )
}
package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.tooling.preview.Preview
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
                AppSwitch(
                    checked = true,
                )

                AppSwitch(
                    checked = false,
                    enabled = false
                )
            }
        }
    }
}


/**
 * [AppSwitch] composable is a custom implementation of a toggle button.
 *
 * @param modifier the [Modifier] to be applied to this switch
 * @param checked whether or not this switch is checked
 * @param enabled controls the enabled state of this switch. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param thumbContent content that will be drawn inside the thumb, expected to measure
 * [SwitchDefaults.IconSize] different states. See [SwitchDefaults.colors].
 * @param colors This contains the colors for the Radio Button
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this switch. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this switch in different states.
 * @param onCheckedChange called when this switch is clicked.
 * */
@Composable
fun AppSwitch(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    thumbContent: @Composable (() -> Unit)? = null,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = AppTheme.colors.onPrimary,
        checkedTrackColor = AppTheme.colors.primary,
        checkedBorderColor = Color.Transparent,
        checkedIconColor = AppTheme.colors.onPrimaryContainer,
        uncheckedThumbColor = AppTheme.colors.outline,
        uncheckedTrackColor = AppTheme.colors.onSecondary,
        uncheckedBorderColor = AppTheme.colors.outline,
        uncheckedIconColor = AppTheme.colors.surfaceVariant,
        disabledCheckedThumbColor = AppTheme.colors.surface
            .copy(AppTheme.alphaValues.level5)
            .compositeOver(AppTheme.colors.surface),
        disabledCheckedTrackColor = AppTheme.colors.onSurface
            .copy(AppTheme.alphaValues.level1)
            .compositeOver(AppTheme.colors.surface),
        disabledCheckedBorderColor = Color.Transparent,
        disabledCheckedIconColor = AppTheme.colors.onSurface
            .copy(AppTheme.alphaValues.level2)
            .compositeOver(AppTheme.colors.surface),
        disabledUncheckedThumbColor = AppTheme.colors.onSurface
            .copy(AppTheme.alphaValues.level2)
            .compositeOver(AppTheme.colors.surface),
        disabledUncheckedTrackColor = AppTheme.colors.surfaceVariant
            .copy(AppTheme.alphaValues.level1)
            .compositeOver(AppTheme.colors.surface),
        disabledUncheckedBorderColor = AppTheme.colors.onSurface
            .copy(AppTheme.alphaValues.level1)
            .compositeOver(AppTheme.colors.surface),
        disabledUncheckedIconColor = AppTheme.colors.surfaceVariant
            .copy(AppTheme.alphaValues.level2)
            .compositeOver(AppTheme.colors.surface),
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        thumbContent = thumbContent,
        enabled = enabled,
        colors = colors,
        interactionSource
    )
}
package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
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
                AppToggleButton(
                    checked = true,
                )

                AppToggleButton(
                    checked = false,
                    enabled = false
                )
            }
        }
    }
}


/**
 * [AppToggleButton] composable is a custom implementation of a toggle button.
 *
 * @param modifier the [Modifier] to be applied to this switch
 * @param checked whether or not this switch is checked
 * @param onCheckedChange called when this switch is clicked.
 * @param enabled controls the enabled state of this switch. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param thumbContent content that will be drawn inside the thumb, expected to measure
 * [SwitchDefaults.IconSize]
 * different states. See [SwitchDefaults.colors].
 * */
@Composable
fun AppToggleButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    thumbContent: (@Composable () -> Unit)? = null,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = AppTheme.colors.onPrimary,
        checkedTrackColor = AppTheme.colors.primary,
        checkedBorderColor = Color.Transparent,
        checkedIconColor = AppTheme.colors.onPrimaryContainer,
        uncheckedThumbColor = AppTheme.colors.outline,
        uncheckedTrackColor = AppTheme.colors.surfaceVariant,
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
    )
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        thumbContent = thumbContent,
        enabled = enabled,
        colors = colors
    )
}
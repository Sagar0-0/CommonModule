package fit.asta.health.designsystemx.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystemx.AppTheme


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
    thumbContent: (@Composable () -> Unit)? = null
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        thumbContent = thumbContent,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppTheme.colorsX.onPrimary,
            checkedTrackColor = AppTheme.colorsX.primary,
            checkedBorderColor = Color.Transparent,
            checkedIconColor = AppTheme.colorsX.onPrimaryContainer,
            uncheckedThumbColor = AppTheme.colorsX.outline,
            uncheckedTrackColor = AppTheme.colorsX.surfaceVariant,
            uncheckedBorderColor = AppTheme.colorsX.outline,
            uncheckedIconColor = AppTheme.colorsX.surfaceVariant,
            disabledCheckedThumbColor = AppTheme.colorsX.surface
                .copy(alpha = 1f)
                .compositeOver(AppTheme.colorsX.surface),
            disabledCheckedTrackColor = AppTheme.colorsX.onSurface
                .copy(alpha = .15f)
                .compositeOver(AppTheme.colorsX.surface),
            disabledCheckedBorderColor = Color.Transparent,
            disabledCheckedIconColor = AppTheme.colorsX.onSurface
                .copy(alpha = .35f)
                .compositeOver(AppTheme.colorsX.surface),
            disabledUncheckedThumbColor = AppTheme.colorsX.onSurface
                .copy(alpha = .35f)
                .compositeOver(AppTheme.colorsX.surface),
            disabledUncheckedTrackColor = AppTheme.colorsX.surfaceVariant
                .copy(alpha = .15f)
                .compositeOver(AppTheme.colorsX.surface),
            disabledUncheckedBorderColor = AppTheme.colorsX.onSurface
                .copy(alpha = .15f)
                .compositeOver(AppTheme.colorsX.surface),
            disabledUncheckedIconColor = AppTheme.colorsX.surfaceVariant
                .copy(alpha = .35f)
                .compositeOver(AppTheme.colorsX.surface),

            )
    )
}
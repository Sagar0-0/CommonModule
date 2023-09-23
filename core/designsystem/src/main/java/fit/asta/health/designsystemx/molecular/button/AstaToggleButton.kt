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
import fit.asta.health.designsystemx.AstaThemeX


// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AstaThemeX {
        Surface {
            Column {
                AstaToggleButton(
                    checked = true,
                )

                AstaToggleButton(
                    checked = false,
                    enabled = false
                )
            }
        }
    }
}


/**
 * [AstaToggleButton] composable is a custom implementation of a toggle button.
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
fun AstaToggleButton(
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
            checkedThumbColor = AstaThemeX.colorsX.onPrimary,
            checkedTrackColor = AstaThemeX.colorsX.primary,
            checkedBorderColor = Color.Transparent,
            checkedIconColor = AstaThemeX.colorsX.onPrimaryContainer,
            uncheckedThumbColor = AstaThemeX.colorsX.outline,
            uncheckedTrackColor = AstaThemeX.colorsX.surfaceVariant,
            uncheckedBorderColor = AstaThemeX.colorsX.outline,
            uncheckedIconColor = AstaThemeX.colorsX.surfaceVariant,
            disabledCheckedThumbColor = AstaThemeX.colorsX.surface
                .copy(alpha = 1f)
                .compositeOver(AstaThemeX.colorsX.surface),
            disabledCheckedTrackColor = AstaThemeX.colorsX.onSurface
                .copy(alpha = .15f)
                .compositeOver(AstaThemeX.colorsX.surface),
            disabledCheckedBorderColor = Color.Transparent,
            disabledCheckedIconColor = AstaThemeX.colorsX.onSurface
                .copy(alpha = .35f)
                .compositeOver(AstaThemeX.colorsX.surface),
            disabledUncheckedThumbColor = AstaThemeX.colorsX.onSurface
                .copy(alpha = .35f)
                .compositeOver(AstaThemeX.colorsX.surface),
            disabledUncheckedTrackColor = AstaThemeX.colorsX.surfaceVariant
                .copy(alpha = .15f)
                .compositeOver(AstaThemeX.colorsX.surface),
            disabledUncheckedBorderColor = AstaThemeX.colorsX.onSurface
                .copy(alpha = .15f)
                .compositeOver(AstaThemeX.colorsX.surface),
            disabledUncheckedIconColor = AstaThemeX.colorsX.surfaceVariant
                .copy(alpha = .35f)
                .compositeOver(AstaThemeX.colorsX.surface),

            )
    )
}
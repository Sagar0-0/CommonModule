package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
                AppCheckBoxButton(
                    checked = true,
                    onCheckedChange = {}
                )

                AppCheckBoxButton(
                    checked = true,
                    enabled = false,
                    onCheckedChange = {}
                )
            }
        }
    }
}


/** [AppCheckBoxButton] allow users to select one or more items from a set. Checkboxes can
 * turn an option on or off.
 *
 * @param modifier the [Modifier] to be applied to this checkbox
 * @param checked whether this checkbox is checked or unchecked
 * @param enabled controls the enabled state of this checkbox.
 * @param colors This variable contains the color of the CheckBox
 * @param interactionSource the [MutableInteractionSource] representing the stream of Interactions
 * for this checkbox. You can create and pass in your own remembered instance to observe
 * Interactions and customize the appearance / behavior of this checkbox in different states.
 * @param onCheckedChange called when this checkbox is clicked.
 * */
@Composable
fun AppCheckBoxButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = AppTheme.colors.primary,
        uncheckedColor = AppTheme.colors.onSurfaceVariant,
        checkmarkColor = AppTheme.colors.onPrimary,
        disabledCheckedColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2),
        disabledUncheckedColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2),
        disabledIndeterminateColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    )
}
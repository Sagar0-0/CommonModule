package fit.asta.health.designsystemx.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                AstaCheckBoxButton(
                    checked = true,
                    onCheckedChange = {}
                )

                AstaCheckBoxButton(
                    checked = true,
                    enabled = false,
                    onCheckedChange = {}
                )
            }
        }
    }
}


/** [AstaCheckBoxButton] allow users to select one or more items from a set. Checkboxes can
 * turn an option on or off.
 *
 * @param modifier the [Modifier] to be applied to this checkbox
 * @param checked whether this checkbox is checked or unchecked
 * @param onCheckedChange called when this checkbox is clicked.
 * @param enabled controls the enabled state of this checkbox.
 * */
@Composable
fun AstaCheckBoxButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = CheckboxDefaults.colors(
            checkedColor = AstaThemeX.colorsX.primary,
            uncheckedColor = AstaThemeX.colorsX.onSurfaceVariant,
            checkmarkColor = AstaThemeX.colorsX.onPrimary,
            disabledCheckedColor = AstaThemeX.colorsX.onSurface.copy(alpha = .35f),
            disabledUncheckedColor = AstaThemeX.colorsX.onSurface.copy(alpha = .35f),
            disabledIndeterminateColor = AstaThemeX.colorsX.onSurface.copy(alpha = .35f)
        )
    )
}
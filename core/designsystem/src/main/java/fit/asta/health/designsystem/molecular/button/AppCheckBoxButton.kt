package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
 * @param onCheckedChange called when this checkbox is clicked.
 * @param enabled controls the enabled state of this checkbox.
 * */
@Composable
fun AppCheckBoxButton(
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
            checkedColor = AppTheme.colorsX.primary,
            uncheckedColor = AppTheme.colorsX.onSurfaceVariant,
            checkmarkColor = AppTheme.colorsX.onPrimary,
            disabledCheckedColor = AppTheme.colorsX.onSurface.copy(alpha = .35f),
            disabledUncheckedColor = AppTheme.colorsX.onSurface.copy(alpha = .35f),
            disabledIndeterminateColor = AppTheme.colorsX.onSurface.copy(alpha = .35f)
        )
    )
}
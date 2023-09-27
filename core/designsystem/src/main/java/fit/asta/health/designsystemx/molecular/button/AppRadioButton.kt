package fit.asta.health.designsystemx.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                AppRadioButton(
                    selected = true,
                    onClick = {}
                )

                AppRadioButton(
                    selected = true,
                    enabled = false,
                    onClick = {}
                )
            }
        }
    }
}


/** [AppRadioButton] buttons allow users to select one option from a set.
 * @param modifier the [Modifier] to be applied to this radio button.
 * @param selected whether this radio button is selected or not.
 * @param enabled Controls the enabled state of the button. When false, this button will
 * not be clickable and will appear disabled to accessibility services
 * @param onClick called when this radio button is clicked.
 */
@Composable
fun AppRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    RadioButton(
        modifier = modifier,
        selected = selected,
        enabled = enabled,
        colors = RadioButtonDefaults.colors(
            selectedColor = AppTheme.colorsX.primary,
            unselectedColor = AppTheme.colorsX.onSurfaceVariant,
            disabledSelectedColor = AppTheme.colorsX.onSurface.copy(alpha = .35f),
            disabledUnselectedColor = AppTheme.colorsX.onSurface.copy(alpha = .35f)
        ),
        onClick = onClick,
    )
}
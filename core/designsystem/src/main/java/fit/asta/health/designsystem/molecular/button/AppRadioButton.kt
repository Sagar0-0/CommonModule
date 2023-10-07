package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
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
 * @param colors [RadioButtonColors] that will be used to resolve the color used for this radio
 * button in different states. See [RadioButtonDefaults.colors].
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this radio button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this radio button in different states.
 * @param onClick called when this radio button is clicked.
 */
@Composable
fun AppRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors(
        selectedColor = AppTheme.colors.primary,
        unselectedColor = AppTheme.colors.onSurfaceVariant,
        disabledSelectedColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2),
        disabledUnselectedColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null

) {
    RadioButton(
        modifier = modifier,
        selected = selected,
        enabled = enabled,
        colors = colors,
        onClick = onClick,
        interactionSource = interactionSource
    )
}
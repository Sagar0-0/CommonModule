package fit.asta.health.designsystemx.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                AstaFilledButton(
                    onClick = {}
                ) {
                    Text(text = "Enabled Button")
                }

                AstaFilledButton(
                    enabled = false,
                    onClick = {}
                ) {
                    Text(text = "Disabled Button")
                }
            }
        }
    }
}


/**
 * Asta filled button with generic content slot. Wraps Material 3 [Button].
 *
 * @param modifier Modifier to be applied to the button.
 * @param onClick Will be called when the user clicks the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param content The button content.
 */
@Composable
fun AstaFilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(
        start = 24.dp,
        top = 8.dp,
        end = 24.dp,
        bottom = 8.dp
    ),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = AstaThemeX.colorsX.primary,
            contentColor = AstaThemeX.colorsX.onPrimary,
            disabledContainerColor = AstaThemeX.colorsX.onSurface.copy(alpha = .15f),
            disabledContentColor = AstaThemeX.colorsX.onSurface.copy(alpha = .35f)
        ),
        contentPadding = contentPadding,
        content = content
    )
}
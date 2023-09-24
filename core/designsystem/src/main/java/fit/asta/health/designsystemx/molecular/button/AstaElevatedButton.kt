package fit.asta.health.designsystemx.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.designsystemx.molecular.texts.LabelTexts

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
                AstaElevatedButton(
                    onClick = {},
                    textToShow = "Enabled Button",
                    leadingIcon = Icons.Default.Person
                )

                AstaElevatedButton(
                    enabled = false,
                    onClick = {},
                    textToShow = "Disabled Button",
                    leadingIcon = Icons.Default.Person
                )
            }
        }
    }
}


/**
 * Asta Elevated button with generic content slot. Wraps Material 3 [Button].
 *
 * @param modifier Modifier to be applied to the button.
 * @param onClick Will be called when the user clicks the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param iconDes This is the description of the Icon which is provided and it is also optional
 */
@Composable
fun AstaElevatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: ImageVector? = null,
    iconDes: String? = null
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = AstaThemeX.colorsX.primary,
            contentColor = AstaThemeX.colorsX.onPrimary,
            disabledContainerColor = AstaThemeX.colorsX.onSurface.copy(alpha = .15f),
            disabledContentColor = AstaThemeX.colorsX.onSurface.copy(alpha = .35f)
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = AstaThemeX.elevationX.extraSmall,
            pressedElevation = AstaThemeX.elevationX.extraSmall,
            focusedElevation = AstaThemeX.elevationX.extraSmall,
            hoveredElevation = AstaThemeX.elevationX.smallMedium,
            disabledElevation = AstaThemeX.elevationX.noElevation
        ),
        contentPadding = PaddingValues(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 8.dp)
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = iconDes,
                modifier = Modifier.padding(end = AstaThemeX.spacingX.extraSmall)
            )
        }

        LabelTexts.Large(
            text = textToShow,
            color = if (enabled)
                AstaThemeX.colorsX.onPrimary
            else
                AstaThemeX.colorsX.onSurface.copy(alpha = .35f)
        )
    }
}
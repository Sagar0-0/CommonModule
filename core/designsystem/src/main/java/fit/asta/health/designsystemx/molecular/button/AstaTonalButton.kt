package fit.asta.health.designsystemx.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
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
                AstaTonalButton(
                    onClick = {},
                    textToShow = "Enabled Button",
                    leadingIcon = Icons.Default.Person
                )

                AstaTonalButton(
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
 * Asta Tonal button with generic content slot. Wraps Material 3 [Button].
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
fun AstaTonalButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: ImageVector? = null,
    iconDes: String? = null
) {

    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = AstaThemeX.colorsX.secondary,
            contentColor = AstaThemeX.colorsX.onSecondary,
            disabledContainerColor = AstaThemeX.colorsX.onSurface.copy(alpha = .15f),
            disabledContentColor = AstaThemeX.colorsX.onSurface.copy(alpha = .35f)
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
                AstaThemeX.colorsX.onSecondary
            else
                AstaThemeX.colorsX.onSurface.copy(alpha = .35f)
        )
    }
}
package fit.asta.health.designsystem.molecular.button

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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

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
                AppElevatedButton(
                    onClick = {},
                    textToShow = "Enabled Button",
                    leadingIcon = Icons.Default.Person
                )

                AppElevatedButton(
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
fun AppElevatedButton(
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
            containerColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.onPrimary,
            disabledContainerColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level1),
            disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = AppTheme.elevation.extraSmall,
            pressedElevation = AppTheme.elevation.extraSmall,
            focusedElevation = AppTheme.elevation.extraSmall,
            hoveredElevation = AppTheme.elevation.smallMedium,
            disabledElevation = AppTheme.elevation.noElevation
        ),
        contentPadding = PaddingValues(
            start = AppTheme.spacing.extraMedium,
            top = AppTheme.spacing.small,
            end = AppTheme.spacing.extraMedium,
            bottom = AppTheme.spacing.small
        )
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = iconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.extraSmall)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}
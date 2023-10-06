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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param textToShow The button text content to be shown content.
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    onClick: () -> Unit
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
            defaultElevation = AppTheme.elevation.level1,
            pressedElevation = AppTheme.elevation.level1,
            focusedElevation = AppTheme.elevation.level1,
            hoveredElevation = AppTheme.elevation.level2,
            disabledElevation = AppTheme.elevation.level0
        ),
        contentPadding = PaddingValues(
            start = AppTheme.spacing.level4,
            top = AppTheme.spacing.level2,
            end = AppTheme.spacing.level4,
            bottom = AppTheme.spacing.level2
        )
    ) {

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.onPrimary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}


/**
 * Asta Elevated button with generic content slot. Wraps Material 3 [Button].
 *
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param iconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint color of the Icon given
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: ImageVector? = null,
    iconDes: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    onClick: () -> Unit
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
            defaultElevation = AppTheme.elevation.level1,
            pressedElevation = AppTheme.elevation.level1,
            focusedElevation = AppTheme.elevation.level1,
            hoveredElevation = AppTheme.elevation.level2,
            disabledElevation = AppTheme.elevation.level0
        ),
        contentPadding = PaddingValues(
            start = AppTheme.spacing.level4,
            top = AppTheme.spacing.level2,
            end = AppTheme.spacing.level4,
            bottom = AppTheme.spacing.level2
        )
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = iconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
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


/**
 * Asta Elevated button with generic content slot. Wraps Material 3 [Button].
 *
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param textToShow The button text content to be shown content.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param iconDes This is the description of the Icon which is provided and it is also optional
 * @param iconTint This is the tint color of the Icon given
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppElevatedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: Painter? = null,
    iconDes: String? = null,
    iconTint: Color = AppTheme.colors.onPrimary,
    onClick: () -> Unit
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
            defaultElevation = AppTheme.elevation.level1,
            pressedElevation = AppTheme.elevation.level1,
            focusedElevation = AppTheme.elevation.level1,
            hoveredElevation = AppTheme.elevation.level2,
            disabledElevation = AppTheme.elevation.level0
        ),
        contentPadding = PaddingValues(
            start = AppTheme.spacing.level4,
            top = AppTheme.spacing.level2,
            end = AppTheme.spacing.level4,
            bottom = AppTheme.spacing.level2
        )
    ) {

        if (leadingIcon != null) {
            Icon(
                painter = leadingIcon,
                contentDescription = iconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
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
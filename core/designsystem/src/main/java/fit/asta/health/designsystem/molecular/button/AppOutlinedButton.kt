package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
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
    name = "Dark Button", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            Column {
                AppOutlinedButton(
                    onClick = {}, textToShow = "Enabled Button", leadingIcon = Icons.Default.Person
                )

                AppOutlinedButton(
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
 * Asta filled button with generic content slot. Wraps Material 3 [OutlinedButton].
 *
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param textToShow The button text is passed here.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param iconDes This is the description of the Icon which is provided and it is also optional
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: ImageVector? = null,
    leadingPainterIcon: Painter? = null,
    iconDes: String? = null,
    iconTint: Color = LocalContentColor.current,
    onClick: () -> Unit,
) {

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
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
                tint = iconTint
            )
        }
        if (leadingPainterIcon != null) {
            Icon(
                painter = leadingPainterIcon,
                contentDescription = iconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.level1)
            )
        }

        CaptionTexts.Level1(
            text = textToShow, color = if (enabled) AppTheme.colors.onSurface
            else AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}
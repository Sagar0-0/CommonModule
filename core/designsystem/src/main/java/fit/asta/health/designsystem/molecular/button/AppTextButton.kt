package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                AppTextButton(
                    onClick = {},
                    textToShow = "Enabled Button",
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.Person
                )

                AppTextButton(
                    enabled = false,
                    onClick = {},
                    textToShow = "Disabled Button",
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.Person
                )
            }
        }
    }
}


/**
 * Asta text button with generic content slot. Wraps Material 3 [TextButton].
 *
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param textToShow The button Text to be shown to the UI.
 * @param leadingIcon This is the leading Icon of the Button which is optional
 * @param leadingIconDes This is the description of the Icon which is provided and it is also optional
 * @param trailingIcon This is the trailing Icon of the Button which is optional
 * @param trailingIconDes This is the description of the Icon which is provided and it is also optional
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textToShow: String,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    onClick: () -> Unit,
) {

    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.primary,
            disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        ), contentPadding = contentPadding
    ) {

        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes,
                modifier = Modifier.padding(end = AppTheme.spacing.extraSmall)
            )
        }

        CaptionTexts.Level1(
            text = textToShow,
            color = if (enabled)
                AppTheme.colors.primary
            else
                AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes,
                modifier = Modifier.padding(start = AppTheme.spacing.extraSmall)
            )
        }
    }
}
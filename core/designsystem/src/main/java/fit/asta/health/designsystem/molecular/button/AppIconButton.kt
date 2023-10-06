package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme

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
                AppIconButton(
                    onClick = {}, imageVector = Icons.Default.ArrowBackIos
                )

                AppIconButton(
                    enabled = false, onClick = {}, imageVector = Icons.Default.ArrowBackIos
                )
            }
        }
    }
}


/**
 * Asta filled Icon button with generic content slot. Wraps Material 3 [IconButton].
 *
 * @param modifier Modifier to be applied to the button.
 * @param imageVector This is the Icon vector for drawing the Icon
 * @param iconDesc This is the description of the Icon
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param iconTint This is the tint of the Icon which will be given to the Icon
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    iconDesc: String? = null,
    enabled: Boolean = true,
    iconTint: Color = AppTheme.colors.onSurface,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = iconDesc,
            tint = if (enabled)
                iconTint
            else
                iconTint.copy(AppTheme.alphaValues.level2)
        )
    }
}

/**
 * Asta filled Icon button with generic content slot. Wraps Material 3 [IconButton].
 *
 * @param modifier Modifier to be applied to the button.
 * @param painter This is the Icon vector for drawing the Icon
 * @param iconDesc This is the description of the Icon
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param iconTint This is the tint of the Icon which will be given to the Icon
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    iconDesc: String? = null,
    enabled: Boolean = true,
    iconTint: Color = AppTheme.colors.onSurface,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = AppTheme.colors.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        ),
        onClick = onClick
    ) {
        Icon(
            painter = painter,
            contentDescription = iconDesc,
            tint = if (enabled)
                iconTint
            else
                iconTint.copy(AppTheme.alphaValues.level2)
        )
    }
}
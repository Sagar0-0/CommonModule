package fit.asta.health.designsystem.molecular.button

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param colors This contains the colors of the Icon Button
 * @param interactionSource  the [MutableInteractionSource] representing the stream of Interactions
 * for this icon button. You can create and pass in your own remembered instance to observe
 * Interactions and customize the appearance / behavior of this icon button in different states.
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onSurface,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        onClick = onClick,
        interactionSource = interactionSource,
        content = content
    )
}


/**
 * Asta filled Icon button with generic content slot. Wraps Material 3 [IconButton].
 *
 * @param modifier Modifier to be applied to the button.
 * @param imageVector This is the Icon vector for drawing the Icon
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param colors This contains the colors of the Icon Button
 * @param iconDesc This is the description of the Icon
 * @param iconTint This is the tint of the Icon which will be given to the Icon
 * @param interactionSource  the [MutableInteractionSource] representing the stream of Interactions
 * for this icon button. You can create and pass in your own remembered instance to observe
 * Interactions and customize the appearance / behavior of this icon button in different states.
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onSurface,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    iconDesc: String? = null,
    iconTint: Color = AppTheme.colors.onSurface,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = imageVector, contentDescription = iconDesc, tint = if (enabled) iconTint
            else iconTint.copy(AppTheme.alphaValues.level2)
        )
    }
}

/**
 * Asta filled Icon button with generic content slot. Wraps Material 3 [IconButton].
 *
 * @param modifier Modifier to be applied to the button.
 * @param painter This is the painter for drawing the Icon
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param colors This contains the colors of the Icon Button
 * @param iconDesc This is the description of the Icon
 * @param iconTint This is the tint of the Icon which will be given to the Icon
 * @param interactionSource  the [MutableInteractionSource] representing the stream of Interactions
 * for this icon button. You can create and pass in your own remembered instance to observe
 * Interactions and customize the appearance / behavior of this icon button in different states.
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(
        containerColor = Color.Transparent,
        contentColor = AppTheme.colors.onSurface,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
    ),
    iconDesc: String? = null,
    iconTint: Color = AppTheme.colors.onSurface,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painter, contentDescription = iconDesc, tint = if (enabled) iconTint
            else iconTint.copy(AppTheme.alphaValues.level2)
        )
    }
}
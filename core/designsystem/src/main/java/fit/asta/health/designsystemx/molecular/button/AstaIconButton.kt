package fit.asta.health.designsystemx.molecular.button

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
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
                AstaIconButton(
                    onClick = {},
                    imageVector = Icons.Default.ArrowBackIos
                )

                AstaIconButton(
                    enabled = false,
                    onClick = {},
                    imageVector = Icons.Default.ArrowBackIos
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
 * @param onClick Will be called when the user clicks the button.
 */
@Composable
fun AstaIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    iconDesc: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = AstaThemeX.colorsX.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AstaThemeX.colorsX.onSurface.copy(alpha = .35f)
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = iconDesc
        )
    }
}
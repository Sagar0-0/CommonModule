package fit.asta.health.designsystem.molecular.chip

import android.content.res.Configuration
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
                AppAssistChip(
                    textToShow = "Enabled",
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.TrackChanges
                ) {}

                AppAssistChip(
                    textToShow = "Disabled",
                    enabled = false,
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.TrackChanges
                ) {}
            }
        }
    }
}


/**
 * Assist chips represent smart or automated actions that can span multiple apps, such as opening a
 * calendar event from the home screen. Assist chips function as though the user asked an assistant
 * to complete the action. They should appear dynamically and contextually in a UI.
 *
 * ![Assist chip image](https://developer.android.com/images/reference/androidx/compose/material3/assist-chip.png)
 *
 * @param modifier the [Modifier] to be applied to this chip
 * @param enabled controls the enabled state of this chip. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow text label for this chip
 * @param leadingIcon optional icon at the start of the chip
 * @param leadingIconDes This is the description for the Leading Icon
 * @param trailingIcon optional icon at the end of the chip
 * @param trailingIconDes This is the description for the Trailing Icon
 * @param iconTint This is the tint of the Icon
 * @param shape defines the shape of this chip's container, border (when [border] is not null), and
 * shadow (when using [elevation])
 * @param colors [ChipColors] that will be used to resolve the colors used for this chip in
 * different states. See [AssistChipDefaults.assistChipColors].
 * @param elevation [ChipElevation] used to resolve the elevation for this chip in different states.
 * This controls the size of the shadow below the chip. Additionally, when the container color is
 * [ColorScheme.surface], this controls the amount of primary color applied as an overlay. See
 * [AssistChipDefaults.assistChipElevation].
 * @param border the border to draw around the container of this chip. Pass `null` for no border.
 * See [AssistChipDefaults.assistChipBorder].
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this chip. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this chip in different states.
 * @param onClick called when this chip is clicked
 */
@Composable
fun AppAssistChip(
    textToShow: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.onSurface,
    shape: Shape = AppTheme.shape.level2,
    colors: ChipColors = AssistChipDefaults.assistChipColors(),
    elevation: ChipElevation? = AssistChipDefaults.assistChipElevation(),
    border: ChipBorder? = AssistChipDefaults.assistChipBorder(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {

    val textLabelComposable: @Composable (() -> Unit) = {
        if (enabled) CaptionTexts.Level2(text = textToShow)
        else CaptionTexts.Level2(
            text = textToShow, color = AppTheme.colors.onSurface.copy(alpha = .35f)
        )
    }
    val leadingIconComposable: @Composable (() -> Unit) = {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDes,
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }

    val trailingIconComposable: @Composable (() -> Unit) = {
        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDes,
                tint = if (enabled)
                    iconTint
                else
                    AppTheme.colors.onSurface.copy(alpha = AppTheme.alphaValues.level2)
            )
        }
    }

    AssistChip(
        onClick = onClick,
        label = textLabelComposable,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (leadingIcon != null) leadingIconComposable else null,
        trailingIcon = if (trailingIcon != null) trailingIconComposable else null,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    )
}
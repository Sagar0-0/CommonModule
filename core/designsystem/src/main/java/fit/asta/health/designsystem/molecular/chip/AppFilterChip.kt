package fit.asta.health.designsystem.molecular.chip

import android.content.res.Configuration
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableChipBorder
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SelectableChipElevation
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
@OptIn(ExperimentalMaterial3Api::class)
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
                AppFilterChip(
                    textToShow = "Enabled",
                    selected = true,
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.TrackChanges
                ) {}

                AppFilterChip(
                    textToShow = "Disabled",
                    selected = false,
                    enabled = false,
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.TrackChanges
                ) {}
            }
        }
    }
}


/**
 * Filter chips use tags or descriptive words to filter content. They can be a good alternative to
 * toggle buttons or checkboxes.
 *
 * ![Filter chip image](https://developer.android.com/images/reference/androidx/compose/material3/filter-chip.png)
 *
 * Tapping on a filter chip toggles its selection state. A selection state [leadingIcon] can be
 * provided (e.g. a checkmark) to be appended at the starting edge of the chip's label.
 *
 * @param modifier the [Modifier] to be applied to this chip
 * @param selected whether this chip is selected or not
 * @param enabled controls the enabled state of this chip. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param textToShow text label for this chip
 * @param leadingIcon optional icon at the start of the chip
 * @param leadingIconDes This is the description for the leading Icon
 * @param trailingIcon optional icon at the end of the chip
 * @param trailingIconDes This is the description for the trailing icon
 * @param iconTint This is the tint of the Icon
 * @param shape defines the shape of this chip's container, border (when [border] is not null), and
 * shadow (when using [elevation])
 * @param colors [SelectableChipColors] that will be used to resolve the colors used for this chip
 * in different states. See [FilterChipDefaults.filterChipColors].
 * @param elevation [SelectableChipElevation] used to resolve the elevation for this chip in
 * different states. This controls the size of the shadow below the chip. Additionally, when the
 * container color is [ColorScheme.surface], this controls the amount of primary color applied as an
 * overlay. See [FilterChipDefaults.filterChipElevation].
 * @param border the border to draw around the container of this chip. Pass `null` for no border.
 * See [FilterChipDefaults.filterChipBorder].
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this chip. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this chip in different states.
 * @param onClick called when this chip is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFilterChip(
    textToShow: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    leadingIconDes: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDes: String? = null,
    iconTint: Color = AppTheme.colors.onSurface,
    shape: Shape = AppTheme.shape.level2,
    colors: SelectableChipColors = FilterChipDefaults.filterChipColors(),
    elevation: SelectableChipElevation? = FilterChipDefaults.filterChipElevation(),
    //border: BorderStroke? = null,
    border: SelectableChipBorder? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {

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

    val textLabelComposable: @Composable (() -> Unit) = {
        if (enabled)
            CaptionTexts.Level2(text = textToShow)
        else
            CaptionTexts.Level2(
                text = textToShow,
                color = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
            )
    }

    FilterChip(
        selected = selected,
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
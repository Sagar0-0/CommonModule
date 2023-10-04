package fit.asta.health.designsystem.components.generic

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

object AppChips {

    /** [AppAssistChip] A composable function that creates an AssistChip, which is a custom chip
     * component for use in your Jetpack Compose UI.
     *  @param onClick called when this chip is clicked
     *  @param label text label for this chip
     *  @param modifier the [Modifier] to be applied to this chip
     *  @param enabled controls the enabled state of this chip.
     *  @param leadingIcon optional icon at the start of the chip, preceding the [label] text
     *  @param trailingIcon optional icon at the end of the chip
     *  @param shape defines the shape of this chip's container
     *  @param colors [ChipColors] that will be used to resolve the colors used for this chip in
     *  different states.
     *  @param elevation [ChipElevation] used to resolve the elevation for this chip in different states.
     *  @param border the border to draw around the container of this chip.
     */

    @Composable
    fun AppAssistChip(
        onClick: () -> Unit,
        label: @Composable () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        shape: Shape = MaterialTheme.shapes.large,
        colors: ChipColors = AssistChipDefaults.assistChipColors(),
        elevation: ChipElevation? = AssistChipDefaults.assistChipElevation(),
        border: ChipBorder? = AssistChipDefaults.assistChipBorder(),
    ) {
        AssistChip(
            onClick = onClick,
            label = label,
            modifier = modifier,
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
        )
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.designsystem.molecular

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.TooltipBoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import fit.asta.health.designsystem.AppTheme


@Composable
fun AppPlainToolTipBox(
    tooltip: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    tooltipState: PlainTooltipState = PlainTooltipState(),
    shape: Shape = CircleShape,
    containerColor: Color = AppTheme.colors.tertiaryContainer,
    contentColor: Color = AppTheme.colors.onTertiaryContainer,
    content: @Composable() (TooltipBoxScope.() -> Unit)
) {
    PlainTooltipBox(
        tooltip = tooltip,
        tooltipState = tooltipState,
        contentColor = contentColor,
        containerColor = containerColor,
        shape = shape,
        modifier = modifier,
        content = content,
    )
}
@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.designsystem.molecular

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import fit.asta.health.designsystem.AppTheme

@Composable
@ExperimentalMaterial3Api
fun AppRichTooltip(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    shape: Shape = TooltipDefaults.richTooltipContainerShape,
    colors: RichTooltipColors = TooltipDefaults.richTooltipColors(
        containerColor = AppTheme.colors.tertiaryContainer,
        contentColor = AppTheme.colors.onTertiaryContainer,
        titleContentColor = AppTheme.colors.tertiary,
        actionContentColor = AppTheme.colors.onTertiary
    ),
    text: @Composable () -> Unit
) {
    RichTooltip(
        modifier = modifier,
        title = title,
        action = action,
        shape = shape,
        colors = colors,
        text = text
    )
}
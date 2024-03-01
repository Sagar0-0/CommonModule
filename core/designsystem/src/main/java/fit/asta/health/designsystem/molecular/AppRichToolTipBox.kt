@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.designsystem.molecular


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.TooltipBoxScope
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import fit.asta.health.designsystem.AppTheme



@Composable
@ExperimentalMaterial3Api
fun AppRichTooltipBox(
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    tooltipState: RichTooltipState = remember { RichTooltipState() },
    shape: Shape = TooltipDefaults.richTooltipContainerShape,
    colors: RichTooltipColors = TooltipDefaults.richTooltipColors(
        containerColor = AppTheme.colors.tertiaryContainer,
        contentColor = AppTheme.colors.onTertiaryContainer,
        titleContentColor = AppTheme.colors.tertiary,
        actionContentColor = AppTheme.colors.onTertiary
    ),
    content: @Composable TooltipBoxScope.() -> Unit
){
    RichTooltipBox(text = text,
        modifier = modifier,
        title = title,
        action = action,
        tooltipState = tooltipState,
        shape = shape,
        colors = colors,
        content = content)
}
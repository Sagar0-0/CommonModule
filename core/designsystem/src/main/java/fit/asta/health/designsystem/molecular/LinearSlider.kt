package fit.asta.health.designsystem.molecular

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fit.asta.health.designsystem.AppTheme

@Composable
fun LinearSlider(
    value :Float,
    modifier: Modifier = Modifier,
    colors: SliderColors = SliderDefaults.colors(
        thumbColor = AppTheme.colors.secondary,
        activeTrackColor = AppTheme.colors.secondary,
        activeTickColor = AppTheme.colors.secondary,
        inactiveTickColor = AppTheme.colors.onSecondary,
        inactiveTrackColor = AppTheme.colors.onSecondary,
        disabledActiveTickColor = AppTheme.colors.onSecondaryContainer,
        disabledActiveTrackColor = AppTheme.colors.onSecondaryContainer,
        disabledInactiveTickColor = AppTheme.colors.onSecondaryContainer,
        disabledInactiveTrackColor = AppTheme.colors.onSecondaryContainer,
        disabledThumbColor = AppTheme.colors.onSecondaryContainer
    ),
    steps : Int,
    enabled : Boolean = true,
    onValueChange: (Float) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    minimumValue : Float,
    maximumValue : Float
){
    //var sliderPosition by remember { mutableStateOf(value) }
    Slider(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        enabled = enabled,
        modifier = modifier,
        colors = colors,
        steps = steps,
        valueRange =  minimumValue..maximumValue,
        interactionSource = interactionSource,
    )
}
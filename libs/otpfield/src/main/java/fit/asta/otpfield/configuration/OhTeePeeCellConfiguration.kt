package fit.asta.otpfield.configuration

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme

data class OhTeePeeCellConfiguration(
    val shape: Shape,
    val backgroundColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
    val textStyle: TextStyle,
    val placeHolderTextStyle: TextStyle,
) {

    companion object {
        val BORDER_WIDTH = 1.dp

        @Composable
        fun withDefaults(
            shape: Shape = AppTheme.shape.level2,
            backgroundColor: Color = AppTheme.colors.surface,
            borderColor: Color = AppTheme.colors.primary,
            borderWidth: Dp = BORDER_WIDTH,
            textStyle: TextStyle = TextStyle(),
            placeHolderTextStyle: TextStyle = textStyle,
        ) = OhTeePeeCellConfiguration(
            shape = shape,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            borderWidth = borderWidth,
            textStyle = textStyle,
            placeHolderTextStyle = placeHolderTextStyle
        )
    }

}
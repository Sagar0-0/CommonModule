package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ForecastSlider(
    dates: List<String> = listOf("0","50","100","150","200","250","300","350","400","450","500","550","600","650","700","750","800","850","900","950","100"),
    onValueChange: (Int) -> Unit = {},
) {
    val (sliderValue, setSliderValue) = remember { mutableFloatStateOf(0f) }
    val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
    val textSize = with(LocalDensity.current) { 10.dp.toPx() }
    val lineHeightDp = 10.dp
    val lineHeightPx = with(LocalDensity.current) { lineHeightDp.toPx() }
    val canvasHeight = 50.dp
    val textPaint = android.graphics.Paint().apply {
        color =  0xffff47586B.toInt()
        textAlign = android.graphics.Paint.Align.CENTER
        this.textSize = textSize
    }

    Column {
        Box(contentAlignment = Alignment.Center) {
            Canvas(
                modifier = Modifier
                    .height(canvasHeight)
                    .fillMaxWidth()
                    .padding(
                        top = canvasHeight
                            .div(2)
                            .minus(lineHeightDp.div(2))
                    )
            ) {
                val yStart = 0f
                val distance = (size.width.minus(2 * drawPadding)).div(dates.size.minus(1))
                dates.forEachIndexed { index, date ->
                    drawLine(
                        color = Color.DarkGray,
                        start = Offset(x = drawPadding + index.times(distance), y = yStart),
                        end = Offset(x = drawPadding + index.times(distance), y = lineHeightPx)
                    )
                    if (index.rem(2) == 1) {
                        this.drawContext.canvas.nativeCanvas.drawText(
                            date,
                            drawPadding + index.times(distance),
                            size.height,
                            textPaint
                        )
                    }
                }
            }
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = sliderValue,
                valueRange = 0f..1000f,
                steps = dates.size.minus(2),
                onValueChange = {
                    setSliderValue(it)
                    onValueChange(it.toInt())
                })
        }
    }

}
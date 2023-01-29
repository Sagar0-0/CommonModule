package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun ForecastSlider(
    dates: List<String> = listOf("0","50","100","150","200","250","300","350","400","450","500","550","600","650","700","750","800","850","900","950","100"),
    onValueChange: (Int) -> Unit = {},
    viewModel: WaterViewModel = hiltViewModel()
) {
    val value by viewModel.sliderInitialValue.collectAsState()
    val (sliderValue, setSliderValue) = remember { mutableStateOf(value) }
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
            if (sliderValue != null) {
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    value = sliderValue,
                    valueRange = 0f..1000f,
                    steps = dates.size.minus(2),
                    //colors = customSliderColors(),
                    onValueChange = {
                        setSliderValue(it)
                        onValueChange(it.toInt())
                    })
            }
        }
    }

}
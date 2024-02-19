package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import kotlin.math.min

@Composable
fun GaugeChart() {
    val density = LocalDensity.current
    val needleBaseSize = with(density) { 1.dp.toPx() }
    val strokeWidth = with(density) { 6.dp.toPx() }
    val textFontSize = with(density) { 16.dp.toPx() }
    val fontPadding = with(density) { 5.dp.toPx() }
    val progress = remember {
        mutableListOf(0.5f)
    }
    val gaugeDegrees = 180
    val startAngle = 180f
    val primaryColor = AppTheme.colors.onSurface

    val needlePaint = remember {
        Paint().apply {
            color = primaryColor
        }
    }
    val textPaint = remember {
        android.graphics.Paint().apply {
            color = primaryColor.toArgb()
            textSize = textFontSize
            textAlign = android.graphics.Paint.Align.CENTER
        }
    }

    // brush with color stops, where each color can have custom proportion
    val brush = Brush.horizontalGradient(
        0.05f to Color.Green,
        0.4f to Color.Yellow,
        0.8f to Color.Red,
    )

    BoxWithConstraints(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center) {
        val canvasSize = min(constraints.maxWidth, constraints.maxHeight)
        val size = Size(canvasSize.toFloat(), canvasSize.toFloat())
        val canvasSizeDp = with(density) { canvasSize.toDp() }
        val w = size.width
        val h = size.height
        val center = Offset(w / 2, h / 2)
        val textY = center.y + textFontSize + fontPadding

        Canvas(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp),
            onDraw = {

                /** Gauge implementation */
                drawArc(
                    brush = brush,
                    startAngle = startAngle,
                    sweepAngle = gaugeDegrees.toFloat(),
                    useCenter = false,
                    size = size,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                drawIntoCanvas { canvas ->

                    /** Needle implementation */

                    canvas.save()

                    // rotate canvas based on progress, to move the needle
                    canvas.rotate(
                        degrees = 32f,
                        pivotX = center.x,
                        pivotY = center.y
                    )

                    //draw Needle shape
                    canvas.drawPath(
                        Path().apply {
                            moveTo(center.x, center.x)
                            lineTo(center.x, center.y + needleBaseSize)
                            lineTo(w / 6, center.y)
                            lineTo(center.x, center.y - 5)
                            close()
                        },
                        needlePaint
                    )

                    canvas.restore()

                    /** Text bellow needle */

                    // draw percentage text
                    canvas.nativeCanvas.drawText(
                        "23".toString(),
                        center.x,
                        textY,
                        textPaint
                    )

                }
            }
        )
        CaptionTexts.Level5(
            text = "Curr UV",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = AppTheme.spacing.level1),
            textAlign = TextAlign.Center
        )
    }
}
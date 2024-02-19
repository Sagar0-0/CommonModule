package fit.asta.health.sunlight.feature.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.sunlight.feature.utils.drawTextMultiline
import fit.asta.health.sunlight.feature.utils.splitAmPm
import fit.asta.health.sunlight.feature.utils.toAmPmFormat
import fit.asta.health.sunlight.feature.utils.toUVIndexColor
import fit.asta.health.sunlight.feature.utils.toUVIndexColorPartGrad
import fit.asta.health.sunlight.remote.model.PieChartInput
import fit.asta.health.sunlight.utils.ChartUtils

@Composable
fun CircularIndexChart() {

}

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius: Float = 500f,
    innerRadius: Float = 300f,
    transparentWidth: Float = 70f,
    input: List<PieChartInput>,
    currUv: String = ""
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    val inputList by remember {
        mutableStateOf(input)
    }
    var isCenterTapped by remember {
        mutableStateOf(false)
    }
    val surfaceColor = AppTheme.colors.surface
    val onSurface = AppTheme.colors.onSurface
    val density = LocalDensity.current
    val needleBaseSize = with(density) { 1.dp.toPx() }
    val needlePaint = remember {
        androidx.compose.ui.graphics.Paint().apply {
            color = onSurface
        }
    }
    val textFontSize = with(density) { 16.dp.toPx() }
    val textPaint = remember {
        Paint().apply {
            color = onSurface.toArgb()
            textSize = textFontSize
            textAlign = android.graphics.Paint.Align.CENTER
            isFakeBoldText = true
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            circleCenter = Offset(x = width / 2f, y = height / 2f)

            val totalValue = input.sumOf {
                it.value
            }
            val anglePerValue = 180f / totalValue
            var currentStartAngle = 180f

            inputList.forEach { pieChartInput ->
                val scale = if (pieChartInput.isTapped) 1.1f else 1.0f
                val angleToDraw = pieChartInput.value * anglePerValue
                scale(scale) {
                    drawArc(
                        brush = Brush.radialGradient(
                            0.7f to pieChartInput.uv.toInt().toUVIndexColorPartGrad(),
                            1f to pieChartInput.uv.toInt().toUVIndexColor(),
                            tileMode = TileMode.Clamp,
                            radius=radius
                        ),
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw,
                        useCenter = true,
                        size = Size(
                            width = radius * 2f,
                            height = radius * 2f
                        ),
                        topLeft = Offset(
                            (width - radius * 2f) / 2f,
                            (height - radius * 2f) / 2f
                        )
                    )
                    currentStartAngle += angleToDraw
                    drawArc(
                        color = onSurface,
                        startAngle = currentStartAngle - 1f,
                        sweepAngle = 1f,
                        useCenter = true,
                        size = Size(
                            width = radius * 2f,
                            height = radius * 2f
                        ),
                        topLeft = Offset(
                            (width - radius * 2f) / 2f,
                            (height - radius * 2f) / 2f
                        )
                    )
                }
                var rotateAngle = (currentStartAngle - angleToDraw / 2f - 90f)
                var factor = 1f
                if (rotateAngle > 90f) {
                    rotateAngle = (rotateAngle + 180).mod(360f)
                    factor = -0.92f
                }

                val percentage = (pieChartInput.value / totalValue.toFloat() * 100).toInt()

                drawContext.canvas.nativeCanvas.apply {
                    if (percentage > 3) {
                        rotate(rotateAngle) {
                            drawTextMultiline(
                                pieChartInput.time.splitAmPm() ?: "",
                                circleCenter.x,
                                circleCenter.y + (radius - (radius - innerRadius) / 2f) * factor,
                                Paint().apply {
                                    textSize = 13.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.White.toArgb()
                                }
                            )
                        }
                    }
                }
                val tabRotation = currentStartAngle - angleToDraw - 90f
                rotate(tabRotation) {
                    drawRoundRect(
                        topLeft = circleCenter,
                        size = Size(12f, radius),
                        color = surfaceColor,
                        cornerRadius = CornerRadius(15f, 15f)
                    )
                }
                rotate(tabRotation + angleToDraw) {
                    drawRoundRect(
                        topLeft = circleCenter,
                        size = Size(12f, radius),
                        color = surfaceColor,
                        cornerRadius = CornerRadius(15f, 15f)
                    )
                }
            }

            if (inputList.first().isTapped) {
                rotate(-90f) {
                    drawRoundRect(
                        topLeft = circleCenter,
                        size = Size(12f, radius * 1.2f),
                        color = Color.Gray,
                        cornerRadius = CornerRadius(15f, 15f)
                    )
                }
            }
            drawContext.canvas.nativeCanvas.apply {
                /* drawArc(
                     circleCenter.x,
                     circleCenter.y,
                 )*/
              /*  drawArc(
                    color = onSurface.copy(0.2f),
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    size = Size(
                        width = innerRadius * 2f + transparentWidth,
                        height = innerRadius * 2f + transparentWidth
                    ),
                    topLeft = Offset(
                        circleCenter.x - innerRadius - (transparentWidth / 2f),
                        circleCenter.y - innerRadius - (transparentWidth / 2f)
                    )
                )*/
                drawArc(
                    color = surfaceColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = true,
                    size = Size(
                        width = innerRadius * 2f,
                        height = innerRadius * 2f
                    ),
                    topLeft = Offset(
                        circleCenter.x - innerRadius,
                        circleCenter.y - innerRadius
                    )
                )
            }


            drawCircle(
                color = onSurface,
                radius = needleBaseSize * 8,
                center = Offset(center.x, center.y + 12f)
            )


            drawIntoCanvas { canvas ->

                /** Needle implementation */
                canvas.save()
                canvas.rotate(
                    degrees = ChartUtils.calculateAngle(
                        input.firstOrNull()?.time ?: "",
                        input.lastOrNull()?.time ?: ""
                    ).toFloat(),
                    pivotX = center.x,
                    pivotY = center.y
                )
                canvas.drawPath(
                    Path().apply {
                        moveTo(center.x, center.x)
                        lineTo(center.x, center.y + needleBaseSize)
                        lineTo(width / 6, center.y)
                        lineTo(center.x, center.y - 12)
                        close()
                    },
                    needlePaint
                )

                canvas.restore()
                /** Text bellow needle */
                canvas.nativeCanvas.drawText(
                    "Curr UV : $currUv",
                    center.x,
                    center.y + 100,
                    textPaint
                )

            }

        }
    }
}


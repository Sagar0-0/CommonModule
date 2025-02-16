package fit.asta.chart.linear.margins

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp
import fit.asta.chart.linear.decoration.LinearDecoration
import fit.asta.chart.linear.interfaces.LinearDataInterface
import fit.asta.chart.linear.interfaces.LinearMarginInterface

/**
 * This is one of the implementations of the [LinearMarginInterface] and it provides with a implementation
 * of how we should draw the Margin
 */
class LinearStringMargin : LinearMarginInterface {

    /**
     * This is the function which contains the actual margin implementation
     *
     * @param linearData This is the data of the Line Chart
     * @param decoration THis is the decoration of the function
     */
    override fun DrawScope.drawMargin(
        linearData: LinearDataInterface,
        decoration: LinearDecoration
    ) {

        linearData.yMarkerList.forEach { point ->

            val bounds = Rect()
            val paint = Paint()

            paint.color = decoration.textColor.toArgb()
            paint.textSize = 12.sp.toPx()
            paint.textAlign = Paint.Align.LEFT
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            paint.getTextBounds(point.value.toString(), 0, point.value.toString().length, bounds)

            val width = bounds.width()

            // This draws the String Marker
            drawContext.canvas.nativeCanvas.drawText(
                point.value.toString(),
                point.xCoordinate,
                point.yCoordinate,
                paint
            )

            // This draws the Lines for the readings parallel to X Axis
            drawLine(
                start = Offset(
                    x = width.toFloat(),
                    y = point.yCoordinate - 12f
                ),
                color = decoration.textColor.copy(alpha = 0.8f),
                end = Offset(
                    x = size.width,
                    y = point.yCoordinate - 12f
                ),
                strokeWidth = 1f
            )
        }

        // This Draws the Y Markers below the Graph
        linearData.xAxisReadings.forEach { currentMarker ->

            // This draws the String Marker
            drawContext.canvas.nativeCanvas.drawText(
                currentMarker.value.toString(),
                currentMarker.xCoordinate,
                currentMarker.yCoordinate,
                Paint().apply {
                    color = decoration.textColor.toArgb()
                    textSize = 12.sp.toPx()
                    textAlign = Paint.Align.CENTER
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                }
            )
        }
    }
}
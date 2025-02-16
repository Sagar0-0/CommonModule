package fit.asta.chart.linear.data

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import fit.asta.chart.linear.interfaces.LinearDataInterface
import fit.asta.chart.util.ChartPoint

/**
 * This is one of the implementation for storing and calculating the data in the chart. It
 * Implements the [LinearDataInterface] Interface
 *
 * @param xAxisReadings These are the readings of the X - Axis
 * @param yAxisReadings These are the readings of the Y - Axis
 * @param yMarkerList This is the list of marker which are present in the Y - Axis
 */
class LinearEmojiData(
    override val yAxisReadings: List<List<ChartPoint<Float>>>,
    override val xAxisReadings: List<ChartPoint<String>>,
    override var yMarkerList: MutableList<ChartPoint<*>> = mutableListOf(),
    val dimension: Int = 50
) : LinearDataInterface {

    /**
     * These are the num of markers in X-Axis
     */
    override val numOfXMarkers: Int = xAxisReadings.size
    override var numOfYMarkers: Int = yMarkerList.size

    /**
     * Upper Y - Axis Reading or the Maximum Reading of the Graph
     */
    private var yUpperReading: Int = Int.MIN_VALUE

    /**
     * Lower Y - Axis Reading or the Maximum Reading of the Graph
     */
    private var yLowerReading: Int = Int.MAX_VALUE

    /**
     * It is the difference of the Upper and Lower Markers divided by the Markers count
     */
    private var yDividend: Int

    init {

        // Storing the upper Bound and Lower bound of Y Markers
        yUpperReading = yMarkerList.size - 1
        yLowerReading = 0

        // Difference between each Y Markers
        yDividend = 1
    }

    /**
     * This is the function which is responsible for the calculations of all the graph related stuff
     *
     * @param size This is the size of the whole canvas which also haves the componentSize in it
     */
    override fun DrawScope.doCalculations(size: Size) {

        // Scale of Y - Axis of the Graph
        val yScale = size.height / numOfYMarkers

        // maximum Width of the Y - Markers
        val yMarkerMaxWidth = calculateYMarkersCoordinates(yScale = yScale)

        // X - Axis Scale
        val xScale = (size.width - yMarkerMaxWidth) / numOfXMarkers

        // This function calculates the Coordinates for the Readings
        calculateReadingsCoordinates(
            xScale = xScale,
            yScale = yScale,
            yMarkerMaxWidth = yMarkerMaxWidth
        )

        // This function calculates the Coordinates for the X - Markers
        calculateXMarkersCoordinates(
            size = size,
            xScale = xScale,
            yMarkerMaxWidth = yMarkerMaxWidth
        )
    }

    /**
     * This function calculates the Y - Axis Markers Coordinates
     *
     * @param yScale This is the scale for the Y - Axis
     */
    private fun calculateYMarkersCoordinates(yScale: Float): Int {

        var maxDimension = 0

        // Calculating all the chart Y - Axis markers in the chart along with their coordinates
        yMarkerList.forEachIndexed { index, point ->

            point.value as BitmapDrawable

            // Current Y Coordinate for the point
            val currentYCoordinate = (yScale * index) - (dimension.toFloat() / 2f)

            val resizedBitmap =
                Bitmap.createScaledBitmap(point.value.bitmap, dimension, dimension, true)

            if (resizedBitmap.width > maxDimension)
                maxDimension = resizedBitmap.width

            // Setting the calculated graph coordinates to the object
            point.setXCoordinate(-24f)
            point.setYCoordinate(currentYCoordinate)

        }
        return maxDimension
    }

    /**
     * This function calculates the Coordinates for the Readings
     *
     * @param xScale This is the scale for the X - Axis
     * @param yScale  This is the scale for the Y - Axis
     * @param yMarkerMaxWidth This is the maximum width of the Y - Markers
     */
    private fun calculateReadingsCoordinates(xScale: Float, yScale: Float, yMarkerMaxWidth: Int) {

        // Taking all the points given and calculating where they will stay in the graph
        yAxisReadings.forEach { pointSet ->

            pointSet.forEachIndexed { index, point ->

                val currentYCoordinate = (yUpperReading - point.value) * yScale / yDividend
                val currentXCoordinate = 48f + (index * xScale) + yMarkerMaxWidth

                // Setting the calculated graph coordinates to the object
                point.setXCoordinate(currentXCoordinate)
                point.setYCoordinate(currentYCoordinate)
            }
        }
    }

    /**
     * This Function calculates the coordinates for the X Markers
     *
     * @param size This is the size of the canvas
     * @param xScale This is the scale for the X - Axis
     * @param yMarkerMaxWidth This is the maximum width of the Y - Markers
     */
    private fun calculateXMarkersCoordinates(size: Size, xScale: Float, yMarkerMaxWidth: Int) {

        // Calculating all the chart X - Axis markers coordinates
        xAxisReadings.forEachIndexed { index, currentMarker ->

            val xCoordinate = (xScale * index) + 48f + yMarkerMaxWidth
            val yCoordinate = size.height

            // Setting the calculated graph coordinates to the object
            currentMarker.setXCoordinate(xCoordinate)
            currentMarker.setYCoordinate(yCoordinate)
        }
    }
}
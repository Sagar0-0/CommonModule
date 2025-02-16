package fit.asta.chart.linear.interfaces

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import fit.asta.chart.linear.data.LinearStringData
import fit.asta.chart.util.ChartPoint

/**
 * This is the Data Interface which has to be implemented by the class which makes a new
 * Implementation for the handling of data and calculations in the graph
 *
 * Implementations for this interface are :- [LinearStringData]
 */
interface LinearDataInterface {

    /**
     * These are the readings of the Y - Axis
     */
    val yAxisReadings: List<List<ChartPoint<*>>>

    /**
     * These are the readings of the X - Axis
     */
    val xAxisReadings: List<ChartPoint<*>>

    /**
     * These are the markers needed in X Axis
     */
    val numOfXMarkers: Int

    /**
     * These are teh num of markers in Y-axis
     */
    val numOfYMarkers: Int

    /**
     * List of all the markers in the Y - Axis
     */
    var yMarkerList: MutableList<ChartPoint<*>>

    /**
     * THis is the function which contains most of the calculation logic of the graph
     */
    fun DrawScope.doCalculations(size: Size)

}
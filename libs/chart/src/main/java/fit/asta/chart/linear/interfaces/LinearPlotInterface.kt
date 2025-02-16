package fit.asta.chart.linear.interfaces

import androidx.compose.ui.graphics.drawscope.DrawScope
import fit.asta.chart.linear.decoration.LinearDecoration
import fit.asta.chart.linear.plots.LinearBarPlot
import fit.asta.chart.linear.plots.LinearLinePlot

/**
 * This is the interface which needs to be every graph plot logic to work in the Library
 *
 * Implementations for this interface are :- [LinearBarPlot] , [LinearLinePlot]
 */
interface LinearPlotInterface {

    /**
     * This function plots the graph points
     */
    fun DrawScope.plotChart(
        linearData: LinearDataInterface,
        decoration: LinearDecoration
    )
}
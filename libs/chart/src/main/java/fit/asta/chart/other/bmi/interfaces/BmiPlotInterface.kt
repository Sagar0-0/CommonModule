package fit.asta.chart.other.bmi.interfaces

import androidx.compose.ui.graphics.drawscope.DrawScope
import fit.asta.chart.other.bmi.decorations.BmiDecorations
import fit.asta.chart.other.bmi.plots.BmiPlot

/**
 * This is the interface which needs to be every bmi graph plot logic to work in the Library
 *
 * Implementations for this interface are :- [BmiPlot]
 */
interface BmiPlotInterface {

    /**
     * This function plots the graph points
     */
    fun DrawScope.plotChart(
        bmiData: BmiDataInterface,
        decoration: BmiDecorations
    )
}
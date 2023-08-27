package fit.asta.chart.other.bmi.plots

import androidx.compose.ui.graphics.drawscope.DrawScope
import fit.asta.chart.other.bmi.decorations.BmiDecorations
import fit.asta.chart.other.bmi.interfaces.BmiDataInterface
import fit.asta.chart.other.bmi.interfaces.BmiPlotInterface


/**
 * This is the Bmi Plot class which implements the [BmiPlotInterface] Interface and makes a Bmi
 * Plotting
 *
 * @param circleRadius This defines the radius of curve of the Circle
 */
class BmiPlot(
    private val circleRadius: Float = 12f
) : BmiPlotInterface {


    /**
     * This is the function which contains the actual margin implementation
     *
     * @param bmiData This is the data of the bmi Chart
     * @param decoration THis is the decoration of the function
     */
    override fun DrawScope.plotChart(
        bmiData: BmiDataInterface,
        decoration: BmiDecorations
    ) {

        // This function draws the Circle point
        drawCircle(
            color = decoration.plotPrimaryColor,
            radius = circleRadius,
            center = bmiData.readingValue.getOffset()
        )
    }
}
package fit.asta.chart.other.bmi.interfaces

import androidx.compose.runtime.Composable
import fit.asta.chart.other.bmi.body.BmiBody
import fit.asta.chart.other.bmi.decorations.BmiDecorations

/**
 * This is the Body Interface which has to be implemented by the class which makes a new
 * Implementation for the handling of body or other composable functions to be drawn in the Chart.
 *
 *  This class is implemented in [BmiBody] and you can see this class too for the basic
 *  implementation of the class
 *
 * @see [BmiBody]
 */
interface BmiBodyInterface {


    /**
     * This function draws the body of the BMI Chart
     *
     * @param decorations this contains the decoration and colors for the Chart
     * @param bmiData This contains the data related to the Chart UI
     */
    @Composable
    fun DrawBody(
        decorations: BmiDecorations,
        bmiData: BmiDataInterface
    )
}
package fit.asta.chart.circular.colorconvention

import androidx.compose.runtime.Composable
import fit.asta.chart.circular.decoration.CircularDecoration
import fit.asta.chart.circular.interfaces.CircularColorConventionInterface
import fit.asta.chart.circular.interfaces.CircularDataInterface


/**
 * This class is the implementation of [CircularColorConventionInterface] which provides the
 * implementations for drawing the color conventions in the canvas
 */
class CircularDefaultColorConvention : CircularColorConventionInterface {


    /**
     * This function draws the color conventions in the canvas. However in this particular case
     * there is no color convention to be drawn
     *
     * @param circularData This object contains the data of the graph
     * @param decoration THis object contains the decorations of the graph
     */
    @Composable
    override fun DrawColorConventions(
        circularData: CircularDataInterface,
        decoration: CircularDecoration
    ) {
        // Do Nothing
    }
}
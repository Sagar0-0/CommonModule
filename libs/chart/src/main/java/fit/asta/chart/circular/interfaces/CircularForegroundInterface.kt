package fit.asta.chart.circular.interfaces

import androidx.compose.ui.graphics.drawscope.DrawScope
import fit.asta.chart.circular.decoration.CircularDecoration
import fit.asta.chart.circular.foreground.CircularDonutForeground
import fit.asta.chart.circular.foreground.CircularDonutTargetForeground
import fit.asta.chart.circular.foreground.CircularRingForeground

/**
 * This interface needs to be implemented by all the classes which wants to make different
 * implementations for drawing the readings in the chart
 *
 * Implementations for this interface are :- [CircularDonutForeground],
 * [CircularDonutTargetForeground],[CircularRingForeground]
 *
 * @property drawForeground This function draws the foreground using its own implementation
 */
interface CircularForegroundInterface {

    fun DrawScope.drawForeground(
        circularData: CircularDataInterface,
        decoration: CircularDecoration
    )
}
package fit.asta.chart.circular.interfaces

import androidx.compose.runtime.Composable
import fit.asta.chart.circular.colorconvention.CircularDefaultColorConvention
import fit.asta.chart.circular.colorconvention.CircularGridColorConvention
import fit.asta.chart.circular.colorconvention.CircularListColorConvention
import fit.asta.chart.circular.colorconvention.CircularTargetColorConvention
import fit.asta.chart.circular.decoration.CircularDecoration

/**
 * This implementation shall be implemented by all the classes which are making color convention
 * implementation
 *
 * Implementations for this interface are :- [CircularDefaultColorConvention],
 * [CircularGridColorConvention],[CircularListColorConvention],[CircularTargetColorConvention]

 *
 * @property DrawColorConventions THis function draws the desired color Convention
 */
interface CircularColorConventionInterface {

    @Composable
    fun DrawColorConventions(
        circularData: CircularDataInterface,
        decoration: CircularDecoration
    )
}
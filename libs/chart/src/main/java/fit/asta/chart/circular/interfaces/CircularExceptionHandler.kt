package fit.asta.chart.circular.interfaces

import fit.asta.chart.circular.decoration.CircularDecoration


/**
 * This Class provides the interface for the Exception handling of the [CircularChartInterface]
 *
 * @property validateDecoration This checks for exceptions from [CircularDecoration] class
 * @property validateAll This calls all the other functions for checking their respective exceptions
 */
interface CircularExceptionHandler {

    fun validateDecoration()

    fun validateAll()
}
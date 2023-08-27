package fit.asta.chart.linear.exceptions

import fit.asta.chart.linear.decoration.LinearDecoration
import fit.asta.chart.linear.interfaces.LinearColorConventionInterface

/**
 * This exception is thrown when the Text List array [LinearColorConventionInterface.textList]
 * contains more text than the list of primary color at decoration class
 * [LinearDecoration.plotPrimaryColor]
 */
class LinearColorConventionMismatch(message: String?) : Exception(message)

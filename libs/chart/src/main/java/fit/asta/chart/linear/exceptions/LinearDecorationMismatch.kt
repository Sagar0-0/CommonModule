package fit.asta.chart.linear.exceptions

import fit.asta.chart.linear.decoration.LinearDecoration
import fit.asta.chart.linear.interfaces.LinearDataInterface

/**
 * This exception is thrown when the color array [LinearDecoration.plotPrimaryColor] and
 * [LinearDecoration.plotSecondaryColor] contains less color than the list of Y - Axis Readings at
 * data class [LinearDataInterface.yAxisReadings]
 */

class LinearDecorationMismatch(message: String?) : Exception(message)
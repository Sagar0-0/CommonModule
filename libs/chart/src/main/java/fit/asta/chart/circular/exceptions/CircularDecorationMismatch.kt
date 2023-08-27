package fit.asta.chart.circular.exceptions

import fit.asta.chart.circular.decoration.CircularDecoration
import fit.asta.chart.circular.interfaces.CircularDataInterface

/**
 * This class is used to throw an exception when the [CircularDecoration.colorList] size is lesser
 * than the [CircularDataInterface.itemsList] size
 */
class CircularDecorationMismatch(message: String?) : Exception(message)
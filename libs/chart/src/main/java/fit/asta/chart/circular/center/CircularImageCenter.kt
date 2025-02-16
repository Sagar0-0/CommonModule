package fit.asta.chart.circular.center

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import fit.asta.chart.circular.decoration.CircularDecoration
import fit.asta.chart.circular.interfaces.CircularCenterInterface
import fit.asta.chart.circular.interfaces.CircularDataInterface

/**
 * This class is the implementation of [CircularCenterInterface] which focuses on providing an
 * implementation to draw an image on the Chart
 *
 * This Class in particular is the implementation to draw texts
 *
 * @param
 */
class CircularImageCenter(
    private val image: ImageVector = Icons.Default.Check,
    private val contentDescription: String? = null
) : CircularCenterInterface {


    /**
     * This function draws an Image (by default a Tick Mark) in the center of the chart
     *
     * @param circularData This object contains the data of the graph
     * @param decoration THis object contains the decorations of the graph
     */
    @Composable
    override fun DrawCenter(
        circularData: CircularDataInterface,
        decoration: CircularDecoration
    ) {

        // Percentage to be shown
        var percentage = circularData.itemsList[1].second / circularData.itemsList[0].second * 100

        if (percentage.isNaN())
            percentage = 0f

        if (percentage >= 100f) {
            Icon(
                imageVector = image,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}
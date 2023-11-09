package fit.asta.health.ui.common.cards.small.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp


/**
 * This function draws the Circle Button Canvas which draws the Circle buttons and add Click event
 * Listeners to them.
 *
 * @param modifier This is the default modifier to be passed from the Parent function
 * @param circleColor This is the color of the Circle
 * @param circleDiameter This is the Diameter of the Circle
 * @param padding This is the padding from the bottom end of the composable
 * @param onClick This is the event/ function that needs to be triggered when the circle is clicked
 */
@Composable
fun CircleCanvas(
    modifier: Modifier = Modifier,
    index: Int,
    circleColor: Color,
    circleDiameter: Dp,
    padding: Dp,
    onClick: () -> Unit
) {

    val painter = rememberVectorPainter(image = Icons.Default.ArrowDropDown)

    Canvas(modifier = modifier.clickable { onClick() }) {

        // The Center of the circle from the Bottom End Corner
        val circleCenter = Offset(
            x = size.width - padding.toPx(),
            y = size.height - padding.toPx() + index * (circleDiameter.toPx() + padding.toPx())
        )

        // This draws the Circle
        drawCircle(
            color = circleColor,
            radius = circleDiameter.toPx() / 2f,
            center = circleCenter
        )

//        translate(left = size.width, top = size.height) {
//            with(painter) {
//                draw(painter.intrinsicSize)
//
//            }
//        }
    }
}
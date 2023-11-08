package fit.asta.health.ui.common.cards.small.canvas

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * This function makes the background for the small cards
 *
 * @param cornerRadius This is the corner radius of the corners of the box
 * @param circleDiameter This is the Diameter of the circle
 */
fun DrawScope.cardBackGround(
    cornerRadius: Dp,
    circleDiameter: Dp
) {

    // Rectangle covering the area above the Arc
    drawRoundRect(
        color = Color(0xFF90D39B),
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        topLeft = Offset.Zero,
        size = Size(
            width = size.width,
            height = size.height - circleDiameter.toPx()
        )
    )

    // Rectangle covering the left side area of the Arc
    drawRoundRect(
        color = Color(0xFF90D39B),
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        topLeft = Offset.Zero,
        size = Size(
            width = size.width - circleDiameter.toPx(),
            height = size.height
        )
    )

    // Path variable to make the arc above the Circle
    val path = Path()

    // Defining the Path Variable path to be taken
    path.moveTo(8.dp.toPx(), 8.dp.toPx())
    path.lineTo(size.width, 8.dp.toPx())
    path.lineTo(
        x = size.width,
        y = size.height - circleDiameter.toPx()
    )

    // This function makes the path curve in the Arc
    path.quadraticBezierTo(
        x1 = size.width - circleDiameter.toPx(),
        y1 = size.height - circleDiameter.toPx(),
        x2 = size.width - circleDiameter.toPx(),
        y2 = size.height
    )

    // Soothing Path extra lines
    path.lineTo(
        x = 8.dp.toPx(),
        y = size.height
    )

    // Drawing the Path
    drawPath(
        path = path,
        color = Color(0xFF90D39B),
    )
}
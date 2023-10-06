package fit.asta.health.designsystem.atomic.modifier

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.cards.AppCard

// Preview Function
@Preview("Dashed Border Light")
@Preview(
    name = "Dashed Border Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppScreen {
        AppCard {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .dashedBorder(
                        width = 1.dp,
                        radius = AppTheme.customSize.level2,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )
        }
    }
}


/** [Modifier.dashedBorder]  is a custom modifier for Jetpack Compose.This modifier allows you to
 * draw a dashed border around a Compose element. The dashed border is drawn using a Paint object
 * with a specified width, radius, and color. The dashes are created using a dashPathEffect on the
 * Paint, which defines the length of the dashes and the spacing between them.
 *
 * @param width: The width of the dashed border. This is specified in density-independent pixels (Dp).
 * @param radius: The radius of the corners of the rounded rectangle forming the border. This is specified in density-independent pixels (Dp).
 * @param color: The color of the dashed border.
 * */

fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) = drawBehind {
    drawIntoCanvas {
        val paint = Paint().apply {
            strokeWidth = width.toPx()
            this.color = color
            style = PaintingStyle.Stroke
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        }
        it.drawRoundRect(
            width.toPx(),
            width.toPx(),
            size.width - width.toPx(),
            size.height - width.toPx(),
            radius.toPx(),
            radius.toPx(),
            paint
        )
    }
}
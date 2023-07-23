package fit.asta.health.common.imgCropper.cropper.util

import android.graphics.Matrix
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.unit.LayoutDirection
import fit.asta.health.common.imgCropper.cropper.model.AspectRatio


/**
 * Creates a [Rect] shape with given aspect ratio.
 */
fun createRectShape(aspectRatio: AspectRatio): GenericShape {
    return GenericShape { size: Size, _: LayoutDirection ->
        val value = aspectRatio.value

        val width = size.width
        val height = size.height
        val shapeSize =
            if (aspectRatio == AspectRatio.Original) Size(width, height)
            else if (value > 1) Size(width = width, height = width / value)
            else Size(width = height * value, height = height)

        addRect(Rect(offset = Offset.Zero, size = shapeSize))
    }
}

/**
 * Scales this path to [width] and [height] from [Path.getBounds] and translates
 * as difference between scaled path and original path
 */
fun Path.scaleAndTranslatePath(
    width: Float,
    height: Float,
) {
    val pathSize = getBounds().size

    val matrix = Matrix()
    matrix.postScale(
        width / pathSize.width,
        height / pathSize.height
    )

    this.asAndroidPath().transform(matrix)

    val left = getBounds().left
    val top = getBounds().top

    translate(Offset(-left, -top))
}
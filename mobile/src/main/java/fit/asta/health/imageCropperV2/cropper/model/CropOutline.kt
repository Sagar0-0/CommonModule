package fit.asta.health.imageCropperV2.cropper.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape


interface CropOutline {
    val id: Int
    val title: String
}

/**
 * Crop outline that contains a [Shape] like [RectangleShape] to draw frame for cropping
 */
interface CropShape : CropOutline {
    val shape: Shape
}

/**
 * Crop outline that contains a [Path] to draw frame for cropping
 */
interface CropPath : CropOutline {
    val path: Path
}

/**
 * Crop outline that contains a [ImageBitmap]  to draw frame for cropping. And blend modes
 * to draw
 */
interface CropImageMask : CropOutline {
    val image: ImageBitmap
}

/**
 * Wrapper class that implements [CropOutline] and is a shape
 * wrapper that contains [RectangleShape]
 */
@Immutable
data class RectCropShape(
    override val id: Int,
    override val title: String,
) : CropShape {
    override val shape: Shape = RectangleShape
}


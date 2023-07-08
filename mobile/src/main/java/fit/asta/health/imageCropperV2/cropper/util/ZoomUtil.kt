package fit.asta.health.imageCropperV2.cropper.util

import androidx.compose.ui.graphics.GraphicsLayerScope
import fit.asta.health.imageCropperV2.cropper.state.TransformState


internal fun getNextZoomLevel(zoomLevel: ZoomLevel): ZoomLevel = when (zoomLevel) {
    ZoomLevel.Mid -> {
        ZoomLevel.Max
    }

    ZoomLevel.Max -> {
        ZoomLevel.Min
    }

    else -> {
        ZoomLevel.Mid
    }
}

/**
 * Update graphic layer with [transformState]
 */
internal fun GraphicsLayerScope.update(transformState: TransformState) {

    // Set zoom
    val zoom = transformState.zoom
    this.scaleX = zoom
    this.scaleY = zoom

    // Set pan
    val pan = transformState.pan
    val translationX = pan.x
    val translationY = pan.y
    this.translationX = translationX
    this.translationY = translationY

    // Set rotation
    this.rotationZ = transformState.rotation
}

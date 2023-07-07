package fit.asta.health.imageCropperV2.preferences.frames.edit

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import fit.asta.health.R
import fit.asta.health.imageCropperV2.cropper.model.AspectRatio
import fit.asta.health.imageCropperV2.cropper.model.CropFrame
import fit.asta.health.imageCropperV2.cropper.model.CropOutline
import fit.asta.health.imageCropperV2.cropper.model.CutCornerCropShape
import fit.asta.health.imageCropperV2.cropper.model.OutlineType
import fit.asta.health.imageCropperV2.cropper.model.OvalCropShape
import fit.asta.health.imageCropperV2.cropper.model.PolygonCropShape
import fit.asta.health.imageCropperV2.cropper.model.RoundedCornerCropShape
import fit.asta.health.imageCropperV2.cropper.model.getOutlineContainer

@Composable
fun CropShapeAddDialog(
    aspectRatio: AspectRatio,
    cropFrame: CropFrame,
    onConfirm: (CropFrame) -> Unit,
    onDismiss: () -> Unit,
) {

    val dstBitmap = ImageBitmap.imageResource(id = R.drawable.weatherimage)

    val outlineType = cropFrame.outlineType

    var outline: CropOutline by remember {
        mutableStateOf(cropFrame.copy().outlines[0])
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            when (outlineType) {
                OutlineType.RoundedRect -> {

                    val shape = outline as RoundedCornerCropShape

                    RoundedCornerCropShapeEdit(
                        aspectRatio = aspectRatio,
                        dstBitmap = dstBitmap,
                        title = outline.title,
                        roundedCornerCropShape = shape
                    ) {
                        outline = it
                    }
                }

                OutlineType.CutCorner -> {
                    val shape = outline as CutCornerCropShape

                    CutCornerCropShapeEdit(
                        aspectRatio = aspectRatio,
                        dstBitmap = dstBitmap,
                        title = outline.title,
                        cutCornerCropShape = shape
                    ) {
                        outline = it
                    }
                }

                OutlineType.Oval -> {

                    val shape = outline as OvalCropShape

                    OvalCropShapeEdit(
                        aspectRatio = aspectRatio,
                        dstBitmap = dstBitmap,
                        title = outline.title,
                        ovalCropShape = shape
                    ) {
                        outline = it
                    }
                }

                OutlineType.Polygon -> {

                    val shape = outline as PolygonCropShape

                    PolygonCropShapeEdit(
                        aspectRatio = aspectRatio,
                        dstBitmap = dstBitmap,
                        title = outline.title,
                        polygonCropShape = shape
                    ) {
                        outline = it
                    }
                }

                else -> Unit
            }
        },
        confirmButton = {
            TextButton(onClick = {

                val newOutlines: List<CropOutline> = cropFrame.outlines
                    .toMutableList()
                    .apply {
                        add(outline)
                    }
                    .toList()

                val newCropFrame = cropFrame.copy(
                    cropOutlineContainer = getOutlineContainer(
                        outlineType = outlineType,
                        index = newOutlines.size - 1,
                        outlines = newOutlines
                    )
                )

                onConfirm(newCropFrame)
            }) {
                Text("Accept")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }
    )
}
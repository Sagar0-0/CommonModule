package fit.asta.health.imageCropperV2.preferences

import androidx.compose.runtime.Composable
import fit.asta.health.imageCropperV2.cropper.settings.CropFrameFactory
import fit.asta.health.imageCropperV2.cropper.settings.CropProperties

@Composable
internal fun PropertySelectionSheet(
    cropFrameFactory: CropFrameFactory,
    cropProperties: CropProperties,
    onCropPropertiesChange: (CropProperties) -> Unit,
) {
    BaseSheet {
        CropPropertySelectionMenu(
            cropFrameFactory = cropFrameFactory,
            cropProperties = cropProperties,
            onCropPropertiesChange = onCropPropertiesChange
        )

        CropGestureSelectionMenu(
            cropProperties = cropProperties,
            onCropPropertiesChange = onCropPropertiesChange
        )
    }
}

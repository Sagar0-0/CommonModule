package fit.asta.health.designsystem.molecular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.smarttoolfactory.cropper.ImageCropper
import com.smarttoolfactory.cropper.model.AspectRatio
import com.smarttoolfactory.cropper.model.OutlineType
import com.smarttoolfactory.cropper.model.OvalCropShape
import com.smarttoolfactory.cropper.settings.CropDefaults
import com.smarttoolfactory.cropper.settings.CropOutlineProperty
import com.smarttoolfactory.cropper.settings.CropProperties
import com.smarttoolfactory.cropper.settings.CropStyle
import com.smarttoolfactory.cropper.settings.CropType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon

@Composable
fun ImageCropperScreen(
    imageBitmap: ImageBitmap,
    cropProperties: CropProperties = CropDefaults.properties(
        cropType = CropType.Static,
        handleSize = 1f,
        aspectRatio = AspectRatio(1 / 1f),
        cropOutlineProperty = CropOutlineProperty(
            OutlineType.Oval,
            OvalCropShape(0, "")
        )
    ),
    cropStyle: CropStyle = CropDefaults.style(),
    onCropClick: (ImageBitmap?) -> Unit
) {

    var croppedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    var crop by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        ImageCropper(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.spacing.level2),
            imageBitmap = imageBitmap,
            contentDescription = "Image Cropper",
            cropStyle = cropStyle,
            crop = crop,
            cropProperties = cropProperties,
            onCropStart = { },
            onCropSuccess = {
                croppedImage = it
                crop = false
                onCropClick(it)
            }
        )
        AppIconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(AppTheme.spacing.level2),
            onClick = {
                crop = true
            }
        ) {
            AppIcon(imageVector = Icons.Filled.Save, contentDescription = "Crop Image")
        }
    }
}

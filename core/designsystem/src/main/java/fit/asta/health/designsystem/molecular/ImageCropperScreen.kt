package fit.asta.health.designsystem.molecular

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.smarttoolfactory.cropper.ImageCropper
import com.smarttoolfactory.cropper.model.AspectRatio
import com.smarttoolfactory.cropper.model.OutlineType
import com.smarttoolfactory.cropper.model.OvalCropShape
import com.smarttoolfactory.cropper.settings.CropDefaults
import com.smarttoolfactory.cropper.settings.CropOutlineProperty
import com.smarttoolfactory.cropper.settings.CropProperties
import com.smarttoolfactory.cropper.settings.CropStyle
import com.smarttoolfactory.cropper.settings.CropType
import fit.asta.health.common.utils.toBitmap
import fit.asta.health.common.utils.toUri
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon

@Composable
fun ImageCropperScreen(
    visible: Boolean,
    uri: Uri?,
    modifier: Modifier = Modifier,
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
    onCropClick: (Uri?) -> Unit
) {
    AnimatedVisibility(visible = visible) {
        val context = LocalContext.current
        var croppedImage by remember { mutableStateOf<ImageBitmap?>(null) }
        val imageBitmap = remember {
            uri?.toBitmap(context)?.asImageBitmap()
        }

        var crop by remember { mutableStateOf(false) }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            if (imageBitmap == null) {
                onCropClick(null)
            } else {
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
                        onCropClick(it.asAndroidBitmap().toUri(context))
                    }
                )
            }
            AppIconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(AppTheme.spacing.level3),
                onClick = {
                    crop = true
                }
            ) {
                AppIcon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Crop Image"
                )
            }
        }
    }
}

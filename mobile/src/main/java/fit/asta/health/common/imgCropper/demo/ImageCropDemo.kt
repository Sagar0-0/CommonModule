package fit.asta.health.common.imgCropper.demo

import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.colorpicker.widget.drawChecker
import fit.asta.health.common.imgCropper.ImageSelectionButton
import fit.asta.health.common.imgCropper.cropper.ImageCropper
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppBottomBar
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.dialog.AppAlertDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCropperScreen(
    onCloseImgCropper: () -> Unit = {},
    onConfirmSelection: (Uri?) -> Unit = {},
) {

    val context = LocalContext.current

    val contentResolver: ContentResolver = context.contentResolver
    val imageFormat = Bitmap.CompressFormat.JPEG

    var angle by remember {
        mutableFloatStateOf(0f)
    }

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var croppedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    var crop by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var isCropping by remember { mutableStateOf(false) }

    AppScaffold(
        topBar = {
            AppTopBar(title = "Crop Image", onBack = onCloseImgCropper)
        },
        bottomBar = {
            AppBottomBar(
                floatingActionButton = {
                    ImageSelectionButton(onImageSelected = { bitmap: ImageBitmap ->
                        imageBitmap = bitmap
                    })
                }
            ) {
                AppIconButton(
                    imageVector = Icons.Filled.Crop
                ) { crop = true }
                AppIconButton(
                    imageVector = Icons.Filled.Rotate90DegreesCw
                ) { angle = (angle + 90) % 360f }
                AppIconButton(
                    imageVector = Icons.Filled.LockReset
                ) { imageBitmap = null }
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    imageBitmap?.let { bitMap ->

                        ImageCropper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            imageBitmap = bitMap,
                            contentDescription = "Image Cropper",
                            crop = crop,
                            onCropStart = {
                                isCropping = true
                            },
                            onCropSuccess = {
                                croppedImage = it
                                isCropping = false
                                crop = false
                                showDialog = true
                            },
                            angle = angle
                        )
                    }
                }

                if (isCropping) {
                    AppCircularProgressIndicator()
                }
            }

            if (showDialog) {
                croppedImage?.let {

                    val contentUri: Uri? = saveImageBitmapToStorageDemo(
                        imageBitmap = it,
                        imageFormat = imageFormat,
                        contentResolver = contentResolver,
                        compressionQuality = 50
                    )

                    ShowCroppedImageDialog(imageBitmap = it, onDismissRequest = {
                        showDialog = !showDialog
                        croppedImage = null
                    }, onApprovedRequest = {
                        onConfirmSelection(
                            contentUri
                        )

                        Log.d("MediaUri", "Uri -> $contentUri,URI ID -> ${
                            contentUri?.let { it1 ->
                                ContentUris.parseId(
                                    it1
                                )
                            }
                        }")
                    }

                    )

                }
            }
        },
    )

}

@Composable
private fun ShowCroppedImageDialog(
    imageBitmap: ImageBitmap,
    onDismissRequest: () -> Unit,
    onApprovedRequest: () -> Unit,
) {
    AppAlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Image(
                modifier = Modifier
                    .drawChecker(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit,
                bitmap = imageBitmap,
                contentDescription = "result"
            )
        }, dismissButton = {
            AppTextButton(
                textToShow = "Dismiss"
            ) {
                onDismissRequest()
            }
        }) {
        AppTextButton(
            textToShow = "Confirm",
            onClick = onApprovedRequest
        )
    }
}
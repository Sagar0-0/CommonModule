package fit.asta.health.common.imgCropper

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.BuildCompat.PrereleaseSdkCheck
import com.google.modernstorage.photopicker.PhotoPicker
import fit.asta.health.designsystem.molecular.button.AppFloatingActionButton
import fit.asta.health.designsystem.molecular.icon.AppIcon

@OptIn(PrereleaseSdkCheck::class)
@SuppressLint("UnsafeOptInUsageError")
@Composable
fun ImageSelectionButton(
    onImageSelected: (ImageBitmap) -> Unit,
) {

    val context = LocalContext.current

    val photoPicker = rememberLauncherForActivityResult(PhotoPicker()) { uris ->
        val uri = uris.firstOrNull() ?: return@rememberLauncherForActivityResult

        val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(context.contentResolver, uri)
            ) { decoder, info, source ->
                decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                decoder.isMutableRequired = true
            }
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        onImageSelected(bitmap.asImageBitmap())

    }

    AppFloatingActionButton(
        onClick = {
            photoPicker.launch(PhotoPicker.Args(PhotoPicker.Type.IMAGES_ONLY, 1))
        }) {
        AppIcon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
    }
}
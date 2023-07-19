package fit.asta.health.imageCropperV2.demo

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.OutputStream

@Composable
fun saveImageBitmapToStorageDemo(
    imageBitmap: androidx.compose.ui.graphics.ImageBitmap,
    imageFormat: Bitmap.CompressFormat,
    contentResolver: ContentResolver,
    compressionQuality: Int
): Uri? {

    val updatedImageBitmap by rememberUpdatedState(imageBitmap)

    val displayName = "image_${System.currentTimeMillis()}.${getImageExtension(imageFormat)}"

    val imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/${getImageExtension(imageFormat)}")
    }

    var uri: Uri? = null
    contentResolver.insert(imageCollection, contentValues)?.let { insertedUri ->
        uri = insertedUri
        val outputStream: OutputStream? = contentResolver.openOutputStream(insertedUri)
        outputStream?.use { stream ->
            // Convert the Compose ImageBitmap to a Bitmap and compress it to the OutputStream
            val bitmap = updatedImageBitmap.asAndroidBitmap()
            if (!bitmap.compress(imageFormat, compressionQuality, stream)) {
                return null
            }
        }
    }

    return uri

}

private fun getImageExtension(imageFormat: Bitmap.CompressFormat): String {
    return when (imageFormat) {
        Bitmap.CompressFormat.JPEG -> "jpg"
        Bitmap.CompressFormat.PNG -> "png"
        else -> throw IllegalArgumentException("Unsupported image format.")
    }
}
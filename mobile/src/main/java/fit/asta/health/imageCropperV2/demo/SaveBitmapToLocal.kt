package fit.asta.health.imageCropperV2.demo

import android.content.Context
import android.graphics.Bitmap.CompressFormat
import android.os.Environment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun saveImageBitmapToStorage(context: Context, imageBitmap: ImageBitmap): String? {
    val filename = "my_image.jpg" // Set a filename for the image
    var fos: OutputStream? = null
    var imagePath: String? = null

    try {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val storageDir =
                ContextCompat.getExternalFilesDirs(context, Environment.DIRECTORY_PICTURES)[0]
            val imageFile = File(storageDir, filename)
            fos = FileOutputStream(imageFile)

            val bitmap = imageBitmap.asAndroidBitmap() // Convert ImageBitmap to Bitmap
            bitmap.compress(CompressFormat.JPEG, 100, fos)

            fos.flush()
            fos.close()
            imagePath = imageFile.absolutePath
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        fos?.close()
    }

    return imagePath
}

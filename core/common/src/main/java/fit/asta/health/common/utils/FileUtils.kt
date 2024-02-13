package fit.asta.health.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.Charset


const val ONE_MEGABYTE: Long = 1024 * 1024

fun Context.loadJSONFromAsset(url: String?): String? {

    val json: String = try {
        val `is` = this.assets.open(url!!)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }

    return json
}

fun Uri.toBitmap(context: Context): Bitmap? {
    var bitmap: Bitmap? = null
    val contentResolver = context.contentResolver
    try {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(contentResolver, this)
        } else {
            val source = ImageDecoder.createSource(contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("UTIL", "uriToBitmap: ${e.message}")
    }
    return bitmap
}

fun Bitmap.toUri(context: Context): Uri? {
    val bytes = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
        MediaStore.Images.Media.insertImage(context.contentResolver, this, "profile_crop", null)
    return Uri.parse(path)
}
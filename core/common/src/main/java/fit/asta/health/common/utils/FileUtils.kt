package fit.asta.health.common.utils

import android.content.Context
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
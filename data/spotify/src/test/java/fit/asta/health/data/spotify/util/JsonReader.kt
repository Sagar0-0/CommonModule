package fit.asta.health.data.spotify.util

import java.io.InputStreamReader
import java.lang.StringBuilder

object JsonReader {

    fun readJsonFile(filename: String): String {
        val inputStream = JsonReader::class.java.getResourceAsStream(filename)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}
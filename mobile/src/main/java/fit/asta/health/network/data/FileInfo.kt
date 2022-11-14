package fit.asta.health.network.data


import java.io.File

data class FileInfo(
    val name: String = "",
    val file: File,
    val mediaType: String
)
package fit.asta.health.network.data


import java.io.File

data class FileInfo(
    val name: String = "",
    val file: File,
    val mediaType: String
)

data class UploadInfo(
    val id: String = "",
    val uid: String,
    val feature: String
)
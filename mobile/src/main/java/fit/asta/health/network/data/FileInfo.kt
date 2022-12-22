package fit.asta.health.network.data


import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.File

data class FileInfo(
    val name: String = "",
    val file: File,
    val mediaType: String
)

data class UploadInfo(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("uid")
    val uid: String,
    @SerializedName("feature")
    val feature: String,
    @Transient
    val filePath: Uri
)
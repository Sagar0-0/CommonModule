package fit.asta.health.network.data


import android.net.Uri
import com.google.gson.annotations.SerializedName

data class FileInfo(
    val name: String = "",
    val file: Uri
)

data class UploadInfo(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("uid")
    val uid: String,
    @SerializedName("name")
    val name: String,
    @Transient
    val filePath: Uri
)
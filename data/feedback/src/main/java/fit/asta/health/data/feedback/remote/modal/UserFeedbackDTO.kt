package fit.asta.health.data.feedback.remote.modal

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFeedbackDTO(
    @SerializedName("ans")
    val ans: List<An> = listOf(),
    @SerializedName("fid")
    val fid: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("qnrId")
    val qnrId: String = "",
    @SerializedName("uid")
    val uid: String = ""
) : Parcelable

@Parcelize
data class An(
    @SerializedName("dtlAns")
    val dtlAns: String = "",
    @SerializedName("media")
    val media: List<Media> = emptyList(),
    val mediaUri: Set<Uri> = emptySet(),
    @SerializedName("opts")
    val opts: List<String> = listOf(""),
    @SerializedName("qid")
    val qid: Int = 0,
    @SerializedName("type")
    val type: Int = 0
) : Parcelable

@Parcelize
data class Media(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = "",
    val localUri: Uri
) : Parcelable
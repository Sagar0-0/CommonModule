package fit.asta.health.data.feedback.remote.modal

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFeedback(
    @SerializedName("ans")
    val answers: List<Answer> = listOf(),
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
data class Answer(
    @SerializedName("dtlAns")
    val detailedAnswer: String = "",
    @SerializedName("media")
    val media: List<Media> = emptyList(),
    val mediaUri: Set<Uri> = emptySet(),
    @SerializedName("opts")
    val options: List<String> = listOf(""),
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
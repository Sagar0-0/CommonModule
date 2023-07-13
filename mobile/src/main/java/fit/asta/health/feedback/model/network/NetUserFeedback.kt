package fit.asta.health.feedback.model.network

import android.net.Uri
import com.google.gson.annotations.SerializedName


data class NetUserFeedback(
    @SerializedName("ans")
    val ans: List<An>,
    @SerializedName("fid")
    val fid: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("qnrId")
    val qnrId: String,
    @SerializedName("uid")
    val uid: String
)

data class An(
    @SerializedName("dtlAns")
    val dtlAns: String?,
    @SerializedName("isDet")
    val isDet: Boolean,
    @SerializedName("media")
    val media: List<Media>?,
    @SerializedName("opts")
    val opts: List<String>?,
    @SerializedName("qid")
    val qid: Int,
    @SerializedName("type")
    val type: Int
)

data class Media(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    val localUri: Uri
)
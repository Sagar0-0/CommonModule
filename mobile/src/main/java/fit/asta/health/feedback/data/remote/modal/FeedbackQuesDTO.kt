package fit.asta.health.feedback.data.remote.modal

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedbackQuesDTO(
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("data")
    val data: NetFeedback = NetFeedback()
) : Parcelable

@Parcelize
data class NetFeedback(
    @SerializedName("date")
    val date: String = "",
    @SerializedName("fid")
    val fid: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("qns")
    val qns: List<Qn> = listOf()
) : Parcelable


@Parcelize
data class Status(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("msg")
    val msg: String = ""
) : Parcelable

@Parcelize
data class Qn(
    @SerializedName("isDet")
    val isDet: Boolean,
    @SerializedName("opts")
    val opts: List<String>?,
    @SerializedName("qn")
    val qn: String,
    @SerializedName("qno")
    val qno: Int,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int
) : Parcelable
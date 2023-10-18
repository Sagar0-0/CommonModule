package fit.asta.health.data.feedback.remote.modal

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedbackQuesDTO(
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
data class Qn(
    @SerializedName("opts")
    val opts: List<String> = listOf(),
    @SerializedName("qn")
    val qn: String,
    @SerializedName("qno")
    val qno: Int,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("isMan")
    val isMandatory: Boolean,
    @SerializedName("ansType")
    val ansType: AnsType
) : Parcelable

@Parcelize
data class AnsType(
    @SerializedName("isDet")
    val isDet: Boolean,
    @SerializedName("min")
    val min: Int,
    @SerializedName("max")
    val max: Int,
) : Parcelable
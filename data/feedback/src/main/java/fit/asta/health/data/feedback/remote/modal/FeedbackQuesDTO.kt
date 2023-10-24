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
    val qn: String = "",
    @SerializedName("qno")
    val qno: Int = 0,
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("isMan")
    val isMandatory: Boolean = false,
    @SerializedName("ansType")
    val ansType: AnsType = AnsType()
) : Parcelable

@Parcelize
data class AnsType(
    @SerializedName("isDet")
    val isDet: Boolean = false,
    @SerializedName("min")
    val min: Int = 0,
    @SerializedName("max")
    val max: Int = 0,
) : Parcelable
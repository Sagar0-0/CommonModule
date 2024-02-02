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
    val questions: List<Question> = listOf()
) : Parcelable

@Parcelize
data class Question(
    @SerializedName("opts")
    val options: List<String> = listOf(),
    @SerializedName("qn")
    val questionText: String = "",
    @SerializedName("qno")
    val questionNo: Int = 0,
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("type")
    val type: FeedbackQuesType = 0,
    @SerializedName("isMan")
    val isMandatory: Boolean = false,
    @SerializedName("ansType")
    val ansType: AnsType = AnsType()
) : Parcelable

typealias FeedbackQuesType = Int

@Parcelize
data class AnsType(
    @SerializedName("isDet")
    val isDetailed: Boolean = false,
    @SerializedName("min")
    val min: Int = 0,
    @SerializedName("max")
    val max: Int = 0,
) : Parcelable

enum class FeedbackQuestionTypes(val type: FeedbackQuesType) {
    UploadFile(1),
    Rating(2),
    McqCard(3),
    TextField(4),
    McqCard2(5)
}
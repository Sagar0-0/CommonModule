package fit.asta.health.tools.walking.model.network.response

import com.google.gson.annotations.SerializedName

data class StepsDataWithUidDate(
    @SerializedName("data")
    val data: UserDataWithDate,
    @SerializedName("data")
    val status: StatusX
)
data class UserDataWithDate(
    @SerializedName("stepsProgressData")
    val stepsProgressData: StepsProgressData,
    @SerializedName("stepsToolData")
    val stepsToolData: StepsToolData
)
data class StepsProgressData(
    @SerializedName("ach")
    val achieve: Detail,
    @SerializedName("cal")
    val calorie: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("rcm")
    val rcm: Detail,
    @SerializedName("rem")
    val rem: Detail,
    @SerializedName("tgt")
    val target: Detail,
    @SerializedName("uid")
    val uid: String
)
data class Detail(
    @SerializedName("dis")
    val distance: Double,
    @SerializedName("dur")
    val duration: Int,
    @SerializedName("steps")
    val steps: Int
)

data class StepsToolData(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: List<PrcX>,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)
data class PrcX(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("values")
    val values: List<ValueX>
)
data class ValueX(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)
data class StatusX(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String
)
package fit.asta.health.data.sleep.model.network.goals

import com.google.gson.annotations.SerializedName

data class SleepGoalData(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("since")
    val since: Int,
    @SerializedName("type")
    val type: Int
)
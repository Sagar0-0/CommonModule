package fit.asta.health.feedback.model.network

import com.google.gson.annotations.SerializedName

data class NetFeedback(
    @SerializedName("dsc")
    val desc: String,
    @SerializedName("id")
    val id: String,
)

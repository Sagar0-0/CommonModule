package fit.asta.health.feedback.model.network.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.feedback.model.network.NetFeedback
import fit.asta.health.network.data.Status

data class NetFeedbackRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val data: NetFeedback
)


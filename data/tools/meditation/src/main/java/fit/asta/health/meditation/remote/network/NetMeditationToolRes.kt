package fit.asta.health.meditation.remote.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetMeditationToolRes(
    @SerializedName("data")
    val `data`: NetMeditationToolResponse,
    @SerializedName("status")
    val status: Status
)

data class NetMeditationToolResponse(
    @SerializedName("meditationProgressData")
    val meditationProgressData: MeditationProgressData,
    @SerializedName("meditationToolData")
    val meditationToolData: MeditationToolData
)

data class Meta(
    @SerializedName("max")
    val max: String,
    @SerializedName("min")
    val min: String
)
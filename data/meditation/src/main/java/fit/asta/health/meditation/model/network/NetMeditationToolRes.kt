package fit.asta.health.meditation.model.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetMeditationToolRes(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
)
data class Data(
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
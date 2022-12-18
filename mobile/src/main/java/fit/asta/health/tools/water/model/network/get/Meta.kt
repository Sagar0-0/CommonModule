package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("max")
    val max: String,
    @SerializedName("min")
    val min: String
)
package fit.asta.health.data.breathing.model.network


import com.google.gson.annotations.SerializedName

data class InsMda(
    @SerializedName("dur")
    val duration: String,
    @SerializedName("lang")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
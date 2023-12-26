package fit.asta.health.data.breathing.model.network


import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
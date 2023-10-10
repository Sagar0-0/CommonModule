package fit.asta.health.meditation.remote.network


import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
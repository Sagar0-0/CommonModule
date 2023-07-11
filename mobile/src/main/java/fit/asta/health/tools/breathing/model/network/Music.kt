package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("dur")
    val duration: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("lang")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
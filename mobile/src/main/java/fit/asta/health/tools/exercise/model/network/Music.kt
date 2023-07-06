package fit.asta.health.tools.exercise.model.network


import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("dur")
    val dur: String,
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
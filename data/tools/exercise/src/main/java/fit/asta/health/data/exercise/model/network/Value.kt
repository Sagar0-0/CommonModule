package fit.asta.health.data.exercise.model.network


import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("id")
    val id: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("dsc")
    val description: String,
    @SerializedName("url")
    val url: String
)
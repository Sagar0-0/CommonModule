package fit.asta.health.tools.meditation.model.network


import com.google.gson.annotations.SerializedName

data class PostRes(
    @SerializedName("dur")
    val duration: Int,
    @SerializedName("exp")
    val exp: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("mode")
    val mode: String,
    @SerializedName("uid")
    val uid: String
)
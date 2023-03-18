package fit.asta.health.tools.walking.model.network.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("act")
    val data: ResponseData,
    @SerializedName("act")
    val status: ResponseStatus
)
data class ResponseData(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("msg")
    val message: String
)
data class ResponseStatus(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String
)
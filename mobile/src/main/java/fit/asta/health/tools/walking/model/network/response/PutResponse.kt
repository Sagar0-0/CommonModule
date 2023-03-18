package fit.asta.health.tools.walking.model.network.response

import com.google.gson.annotations.SerializedName

data class PutResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: Status
)
data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String
)
data class Data(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("msg")
    val message: String
)
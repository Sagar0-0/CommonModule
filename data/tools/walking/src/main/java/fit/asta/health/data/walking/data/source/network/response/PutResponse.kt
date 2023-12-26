package fit.asta.health.data.walking.data.source.network.response


import com.google.gson.annotations.SerializedName

data class PutResponse(
    @SerializedName("data")
    val `data`: Data1,
    @SerializedName("status")
    val status: Status1
)
data class Status1(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)
data class Data1(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("msg")
    val msg: String
)
package fit.asta.health.auth.fcm.remote


import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName("deviceId")
    val deviceId: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("timeStamp")
    val timeStamp: String = "",
    @SerializedName("token")
    val token: String = "",
    @SerializedName("type")
    val type: Int = 1,
    @SerializedName("uid")
    val uid: String = ""
)
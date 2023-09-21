package fit.asta.health.auth.fcm.remote


import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("flag")
    val flag: Boolean = false,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("msg")
    val msg: String = ""
)
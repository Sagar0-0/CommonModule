package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName

data class BasicProfileResponse(
    @SerializedName("flag")
    val flag: Boolean = false,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("msg")
    val msg: String = ""
)
package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName

data class BasicProfileResponse(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Data(
        @SerializedName("flag")
        val flag: Boolean = false,
        @SerializedName("id")
        val id: String = "",
        @SerializedName("msg")
        val msg: String = ""
    )

    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}
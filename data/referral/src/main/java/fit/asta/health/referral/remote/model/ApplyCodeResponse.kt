package fit.asta.health.referral.remote.model


import com.google.gson.annotations.SerializedName

data class ApplyCodeResponse(
    @SerializedName("data")
    val `data`: UserDetails? = null,
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}
package fit.asta.health.data.profile.remote.model


import com.google.gson.annotations.SerializedName

data class CheckReferralDTO(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Data(
        @SerializedName("mail")
        val mail: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("pic")
        val pic: String = "",
        @SerializedName("prime")
        val prime: Boolean = false
    )

    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}
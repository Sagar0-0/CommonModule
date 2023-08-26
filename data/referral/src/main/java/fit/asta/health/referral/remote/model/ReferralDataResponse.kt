package fit.asta.health.referral.remote.model


import com.google.gson.annotations.SerializedName

data class ReferralDataResponse(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Data(
        @SerializedName("ReferralCode")
        val referralCode: ReferralCode = ReferralCode(),
        @SerializedName("ReferredByUsersDetails")
        val referredByUsersDetails: UserDetails? = null,
        @SerializedName("ReferredUsers")
        val referredUsers: List<UserDetails>? = null
    ) {
        data class ReferralCode(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("refCode")
            val refCode: String = "",
            @SerializedName("uid")
            val uid: String = ""
        )
    }

    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}
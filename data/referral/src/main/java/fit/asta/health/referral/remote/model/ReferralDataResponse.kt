package fit.asta.health.referral.remote.model


import com.google.gson.annotations.SerializedName

data class ReferralDataResponse(
    @SerializedName("ReferralCode")
    val referralCode: ReferralCode = ReferralCode(),
    @SerializedName("ReferredByUserDetail")
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
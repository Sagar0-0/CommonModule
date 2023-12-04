package fit.asta.health.referral.remote.model


import com.google.gson.annotations.SerializedName

data class ReferralDataResponse(
    @SerializedName("ReferralCode")
    val referralCode: ReferralCode = ReferralCode(),
    @SerializedName("ReferredByUserDetail")
    val referredByUsersDetails: UserDetails? = null,
    @SerializedName("ReferredUsers")
    val referredUsers: List<UserDetails>? = null,
    @SerializedName("statsData")
    val referralStats: ReferralStats = ReferralStats()
) {
    data class ReferralCode(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("refCode")
        val refCode: String = "",
        @SerializedName("uid")
        val uid: String = ""
    )

    data class ReferralStats(
        @SerializedName("refAmt")
        val totalIncome: Int = 0,
        @SerializedName("nonSubReferee")
        val nonPremiumUsers: Int = 0,
        @SerializedName("subReferee")
        val premiumUsers: Int = 0
    )
}
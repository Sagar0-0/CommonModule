package fit.asta.health.referral.remote.model


import com.google.gson.annotations.SerializedName

data class ReferralDataResponse(
    @SerializedName("rflDtls")
    val referralDetails: ReferralDetails = ReferralDetails(),
    @SerializedName("rfr")
    val referredByUsersDetails: UserDetails? = null,
    @SerializedName("rfes")
    val referredUsers: List<UserDetails>? = null,
    @SerializedName("statsData")
    val referralStats: ReferralStats = ReferralStats()
) {
    data class ReferralDetails(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("rflCode")
        val refCode: String = "",
        @SerializedName("rfr")
        val refererId: String? = null,
        @SerializedName("rfe")
        val refereeIds: List<String>? = null,
        @SerializedName("uid")
        val uid: String = ""
    )

    data class ReferralStats(
        @SerializedName("rflAmt")
        val totalIncome: Int = 0,
        @SerializedName("nonSubRfe")
        val nonPremiumUsers: Int = 0,
        @SerializedName("subRfe")
        val premiumUsers: Int = 0
    )
}
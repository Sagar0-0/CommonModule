package fit.asta.health.subscription.remote.model


import com.google.gson.annotations.SerializedName


typealias SubscriptionType = String
typealias DurationType = String
typealias UserSubscribedPlanStatus = Int
typealias DiscountUnit = Int

data class SubscriptionResponse(
    @SerializedName("subscriptionPlans")
    val subscriptionPlans: SubscriptionPlans = SubscriptionPlans(),
    @SerializedName("userSubscribedPlan")
    val userSubscribedPlan: UserSubscribedPlan? = null,
    @SerializedName("offers")
    val offers: List<Offer> = listOf()
) {
    data class SubscriptionPlans(
        @SerializedName("dsc")
        val dsc: String = "",
        @SerializedName("con")
        val country: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("ttl")
        val ttl: String = "",
        @SerializedName("types")
        val subscriptionPlanTypes: List<SubscriptionPlanType> = listOf()
    ) {
        data class SubscriptionPlanType(
            @SerializedName("fea")
            val subscriptionPlanFeatures: List<SubscriptionPlanFeature> = listOf(),
            @SerializedName("id")
            val subscriptionType: SubscriptionType = "",
            @SerializedName("plans")
            val subscriptionDurationPlans: List<SubscriptionDurationPlan> = listOf(),
            @SerializedName("ttl")
            val planName: String = "",
            @SerializedName("type")
            val type: Int = 0,
            @SerializedName("url")
            val imageUrl: String = ""
        ) {
            data class SubscriptionPlanFeature(
                @SerializedName("dsc")
                val dsc: String = "",
                @SerializedName("ttl")
                val ttl: String = "",
                @SerializedName("url")
                val url: String = ""
            )

            data class SubscriptionDurationPlan(
                @SerializedName("default")
                val default: Int = 0,
                @SerializedName("dsc")
                val dsc: String = "",
                @SerializedName("id")
                val durationType: DurationType = "",
                @SerializedName("price")
                val priceMRP: String = "",
                @SerializedName("cur")
                val currency: Int = 0,
                @SerializedName("curSym")
                val curSym: String = "",
                @SerializedName("sub")
                val sub: String = "",
                @SerializedName("tag")
                val tag: String = "",
                @SerializedName("ttl")
                val durationTitle: String = "",
                @SerializedName("discount")
                val discountAmount: String = "",
                @SerializedName("disUnit")
                val discountUnit: DiscountUnit = DiscountUnitType.PERCENTAGE.code,
                val tax: String = "",//TODO
                val emi: String? = null,//TODO
            )
        }
    }

    data class UserSubscribedPlan(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("plan")
        val plan: String = "",
        @SerializedName("type")
        val type: String = "",
        @SerializedName("uid")
        val uid: String = "",
        @SerializedName("subOn")
        val subOn: String = "",
        @SerializedName("expBy")
        val expBy: String = "",
        @SerializedName("sts")
        val status: UserSubscribedPlanStatus = 0
    )
}

fun UserSubscribedPlanStatus.getUserSubscribedPlanStatusType() =
    UserSubscribedPlanStatusType.entries.first { this == it.code }

enum class UserSubscribedPlanStatusType(val code: UserSubscribedPlanStatus) {
    ACTIVE(1),
    INACTIVE(2),
    TEMPORARY_INACTIVE(3)
}


fun DiscountUnit.getDiscountUnitType() =
    DiscountUnitType.entries.first { this == it.code }

enum class DiscountUnitType(val code: DiscountUnit) {
    PERCENTAGE(18),
    RUPEE(23)
}




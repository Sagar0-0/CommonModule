package fit.asta.health.subscription.remote.model


import com.google.gson.annotations.SerializedName

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
        val categories: List<SubscriptionPlanCategory> = listOf()
    ) {
        data class SubscriptionPlanCategory(
            @SerializedName("fea")
            val feature: List<Feature> = listOf(),
            @SerializedName("id")
            val subscriptionType: String = "",
            @SerializedName("plans")
            val durations: List<Duration> = listOf(),
            @SerializedName("ttl")
            val title: String = "",
            @SerializedName("type")
            val type: Int = 0,
            @SerializedName("url")
            val imgUrl: String = ""
        ) {
            data class Feature(
                @SerializedName("dsc")
                val dsc: String = "",
                @SerializedName("ttl")
                val ttl: String = "",
                @SerializedName("url")
                val url: String = ""
            )

            data class Duration(
                @SerializedName("default")
                val default: Int = 0,
                @SerializedName("dsc")
                val dsc: String = "",
                @SerializedName("id")
                val durationType: String = "",
                @SerializedName("price")
                val price: String = "",
                @SerializedName("cur")
                val currency: String = "",
                @SerializedName("curSym")
                val curSym: String = "",
                @SerializedName("sub")
                val sub: String = "",
                @SerializedName("tag")
                val tag: String = "",
                @SerializedName("ttl")
                val ttl: String = "",
                val discount: String = "",//TODO
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
        val sts: Boolean = false
    )
}

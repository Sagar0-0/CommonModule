package com.example.subscription.data.model


import com.google.gson.annotations.SerializedName

data class SubscriptionResponse(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Data(
        @SerializedName("subscriptionPlans")
        val subscriptionPlans: SubscriptionPlans = SubscriptionPlans(),
        @SerializedName("userSubscribedPlan")
        val userSubscribedPlan: UserSubscribedPlan? = null,
        val pendingPlan: UserSubscribedPlan? = null
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
            val categories: List<Category> = listOf()
        ) {
            data class Category(
                @SerializedName("fea")
                val feature: List<Feature> = listOf(),
                @SerializedName("id")
                val id: String = "",
                @SerializedName("plans")
                val durations: List<Duration> = listOf(),
                @SerializedName("ttl")
                val ttl: String = "",
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
                    val id: String = "",
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
                    val ttl: String = ""
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

    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}
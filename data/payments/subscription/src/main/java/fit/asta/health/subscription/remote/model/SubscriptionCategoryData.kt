package fit.asta.health.subscription.remote.model

import com.google.gson.annotations.SerializedName

typealias SubscriptionType = Int
data class SubscriptionCategoryData(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("type")
    val type: SubscriptionType = 1,
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("dsc")
    val desc: String = "",
    @SerializedName("url")
    val imageUrl: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("cur")
    val cur: Int = 0,
    @SerializedName("sym")
    val sym: String = "",
)

enum class SubscriptionTypes(val type: SubscriptionType) {
    BRONZE(1),
    SILVER(2),
    GOLD(3),
    PLATINUM(4)
}
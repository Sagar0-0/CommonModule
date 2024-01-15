package fit.asta.health.subscription.remote.model

import com.google.gson.annotations.SerializedName

data class SubscriptionCategoryData(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("type")
    val type: Int = 0,
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
package fit.asta.health.subscription.remote.model


import com.google.gson.annotations.SerializedName

data class UserSubscribedPlan(
    @SerializedName("dsc")
    val dsc: String = "",
    @SerializedName("url")
    val imageUrl: String = "",
    @SerializedName("expBy")
    val expBy: String = "",
    @SerializedName("plan")
    val productId: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("sts")
    val sts: Int = 0,
    @SerializedName("sub")
    val sub: String = "",
    @SerializedName("subOn")
    val subOn: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("type")
    val categoryId: String = "",
    @SerializedName("uid")
    val uid: String = ""
)


enum class UserSubscribedPlanStatusType(val code: Int) {
    NOT_BOUGHT(0),
    ACTIVE(1),
    INACTIVE(2),
    TEMPORARY_INACTIVE(3)
}
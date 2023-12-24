package fit.asta.health.discounts.remote.model

import com.google.gson.annotations.SerializedName

data class CouponRequest(
    @SerializedName("type")
    val productType: String = ProductType.SUBSCRIPTION.type,
    @SerializedName("uid")
    val userId: String = "",
    @SerializedName("couponCode")
    val couponCode: String = "",
    @SerializedName("amt")
    val productMRP: Double = 0.0,
)

enum class ProductType(val type: String) {
    SUBSCRIPTION("1")
}

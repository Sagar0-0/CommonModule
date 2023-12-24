package fit.asta.health.discounts.remote.model

import com.google.gson.annotations.SerializedName

data class CouponRequest(
    @SerializedName("type")
    val productId: String = "",
    @SerializedName("uid")
    val userId: String = "",
    @SerializedName("couponCode")
    val couponCode: String = "",
    @SerializedName("amt")
    val productMRP: String = "",
)

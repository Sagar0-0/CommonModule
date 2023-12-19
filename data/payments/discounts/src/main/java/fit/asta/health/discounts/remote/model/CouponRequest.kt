package fit.asta.health.discounts.remote.model

data class CouponRequest(
    val productId: String = "",
    val userId: String = "",
    val couponCode: String = "",
    val productMRP: String = "",
)

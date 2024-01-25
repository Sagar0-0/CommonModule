package fit.asta.health.discounts.remote.model

data class CouponRequest(
    val productType: Int = ProductType.SUBSCRIPTION.type,
    val couponCode: String = "",
    val productMRP: Double = 0.0,
)

enum class ProductType(val type: Int) {
    SUBSCRIPTION(1)
}

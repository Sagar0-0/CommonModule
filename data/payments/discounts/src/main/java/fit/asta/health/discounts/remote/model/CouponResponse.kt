package fit.asta.health.discounts.remote.model


import com.google.gson.annotations.SerializedName

data class CouponResponse(
    @SerializedName("couponDtls")
    val couponDetails: CouponDetails = CouponDetails(),
    @SerializedName("disAmt")
    val discountAmount: Double = 0.0,
    @SerializedName("finalAmt")
    val finalAmount: Double = 0.0
) {
    data class CouponDetails(
        @SerializedName("areas")
        val couponAreas: List<CouponArea> = listOf(),
        @SerializedName("code")
        val code: String = "",
        @SerializedName("dis")
        val discount: Int = 0,
        @SerializedName("dsc")
        val description: String = "",
        @SerializedName("end")
        val endDate: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("start")
        val startDate: String = "",
        @SerializedName("sts")
        val status: Int = 0,
        @SerializedName("ttl")
        val title: String = "",
        @SerializedName("unit")
        val unit: Int = 0,
        @SerializedName("url")
        val imageUrl: String = ""
    ) {
        data class CouponArea(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("type")
            val type: Int = 0
        )
    }
}
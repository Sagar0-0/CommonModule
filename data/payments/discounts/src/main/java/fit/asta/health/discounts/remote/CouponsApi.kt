package fit.asta.health.discounts.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.discounts.remote.model.CouponResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CouponsApi {
    @GET("payment/coupons/details/get?")
    suspend fun getCouponCodeDetails(
        @Query("type")
        productType: Int,
        @Query("uid")
        userId: String,
        @Query("couponCode")
        couponCode: String,
        @Query("amt")
        productMRP: Double,
    ): Response<CouponResponse>
}
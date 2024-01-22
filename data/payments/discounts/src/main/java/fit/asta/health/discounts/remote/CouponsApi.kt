package fit.asta.health.discounts.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.discounts.remote.model.CouponRequest
import fit.asta.health.discounts.remote.model.CouponResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface CouponsApi {
    @GET("payment/coupons/details/get/")
    suspend fun getCouponCodeDetails(
        @Body couponRequest: CouponRequest
    ): Response<CouponResponse>
}
package fit.asta.health.discounts.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.discounts.remote.model.CouponRequest
import fit.asta.health.discounts.remote.model.CouponResponse
import retrofit2.http.GET

interface CouponsApi {
    @GET("onboarding/get/")
    suspend fun getCouponCodeDetails(couponRequest: CouponRequest): Response<CouponResponse>
}
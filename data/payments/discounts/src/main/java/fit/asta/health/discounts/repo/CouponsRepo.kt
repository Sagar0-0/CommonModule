package fit.asta.health.discounts.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.discounts.remote.model.CouponRequest
import fit.asta.health.discounts.remote.model.CouponResponse

interface CouponsRepo {
    suspend fun getCouponCodeDetails(couponRequest: CouponRequest): ResponseState<CouponResponse>
}
package fit.asta.health.referral.api

import fit.asta.health.referral.model.ApplyCodeResponse
import fit.asta.health.referral.model.ReferralDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReferralApi {

    @GET("payment/referral/code/get/?")
    suspend fun getData(@Query("uid") uid: String): ReferralDataResponse

    @GET("payment/referral/code/check/?")
    suspend fun applyCode(
        @Query("refCode") refCode: String,
        @Query("uid") uid: String
    ): ApplyCodeResponse


}
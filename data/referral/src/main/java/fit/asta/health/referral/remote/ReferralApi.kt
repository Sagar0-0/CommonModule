package fit.asta.health.referral.remote

import fit.asta.health.referral.remote.model.ReferralDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReferralApi {

    @GET("payment/referral/code/get/?")
    suspend fun getData(@Query("uid") uid: String): ReferralDataResponse

}
package fit.asta.health.discounts.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.discounts.remote.model.DiscountsData
import retrofit2.http.GET

interface DiscountsApi {
    @GET("onboarding/get/")
    suspend fun getData(): Response<List<DiscountsData>>
}
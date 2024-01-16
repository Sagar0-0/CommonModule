package fit.asta.health.offers.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.offers.remote.model.OffersData
import retrofit2.http.GET

interface OffersApi {
    @GET("offers/all/get/")
    suspend fun getOffers(): Response<List<OffersData>>

}
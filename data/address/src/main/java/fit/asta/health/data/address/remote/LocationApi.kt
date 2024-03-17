package fit.asta.health.data.address.remote

import fit.asta.health.data.address.remote.modal.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("place/textsearch/json")
    suspend fun search(
        @Query("query") query: String,
        @Query("key") key: String
    ): SearchResponse

    @GET("place/textsearch/json")
    suspend fun searchBiased(
        @Query("location") latLng: String,
        @Query("rankBy") rankBy: String,
        @Query("query") query: String,
        @Query("key") key: String
    ): SearchResponse
}
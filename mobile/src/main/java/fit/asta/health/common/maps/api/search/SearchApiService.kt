package fit.asta.health.common.maps.api.search

import fit.asta.health.common.maps.modal.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

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
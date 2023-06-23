package fit.asta.health.common.location.maps.api

import fit.asta.health.common.location.maps.modal.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("place/textsearch/json")
    suspend fun search(
        @Query("query") query: String,
        @Query("key") key: String
    ): SearchResponse
}
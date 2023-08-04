package fit.asta.health.common.maps.api.search

import fit.asta.health.common.maps.modal.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("place/findplacefromtext/json")
    suspend fun search(
        @Query("query") query: String,
        @Query("key") key: String
    ): SearchResponse
}
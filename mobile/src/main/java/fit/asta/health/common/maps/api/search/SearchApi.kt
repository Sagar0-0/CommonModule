package fit.asta.health.common.maps.api.search

import fit.asta.health.common.maps.modal.SearchResponse

interface SearchApi {
    suspend fun search(query: String, key: String): SearchResponse
    suspend fun searchBiased(
        location: String,
        rankBy: String,
        query: String,
        key: String
    ): SearchResponse
}
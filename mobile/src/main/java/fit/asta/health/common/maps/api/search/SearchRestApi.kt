package fit.asta.health.common.maps.api.search

import fit.asta.health.common.utils.NetworkUtil
import okhttp3.OkHttpClient

class SearchRestApi(baseUrl: String, client: OkHttpClient) : SearchApi {

    private val apiService: SearchApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(SearchApiService::class.java)

    override suspend fun search(query: String, key: String) = apiService.search(query, key)

}
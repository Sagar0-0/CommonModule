package fit.asta.health.tools.sunlight.model.api

import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.tools.sunlight.model.network.response.ResponseData
import okhttp3.OkHttpClient


//Health Tool - Water Endpoints
class SunlightRestApi(client: OkHttpClient) :
    SunlightApi {

    private val apiService: SunlightService = NetworkUtil
        .getRetrofit(client)
        .create(SunlightService::class.java)

    override suspend fun getSunlightTool(
        userId: String,
        latitude: String,
        longitude: String,
        date: String,
        location: String
    ): ResponseData {
        return apiService.getSunlightTool(
            userId = userId,
            latitude = latitude,
            longitude = longitude,
            date = date,
            location = location
        )
    }
}
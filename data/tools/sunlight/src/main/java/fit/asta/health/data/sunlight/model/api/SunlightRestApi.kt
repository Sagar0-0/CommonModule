package fit.asta.health.data.sunlight.model.api

import fit.asta.health.data.sunlight.model.network.response.ResponseData
import fit.asta.health.network.utils.NetworkUtil
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
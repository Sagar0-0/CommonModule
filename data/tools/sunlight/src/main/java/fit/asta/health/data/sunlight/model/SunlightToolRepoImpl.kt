package fit.asta.health.data.sunlight.model

import fit.asta.health.data.sunlight.model.api.SunlightApi
import fit.asta.health.data.sunlight.model.network.response.ResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SunlightToolRepoImpl(
    private val remoteApi: SunlightApi,
) : SunlightToolRepo {

    override suspend fun getSunlightTool(
        userId: String,
        latitude: String,
        longitude: String,
        date: String,
        location: String
    ): Flow<ResponseData.SunlightToolData> {
        return flow {
            emit(
                remoteApi.getSunlightTool(
                    userId = userId,
                    latitude = latitude,
                    longitude = longitude,
                    date = date,
                    location = location
                ).data
            )
        }
    }
}
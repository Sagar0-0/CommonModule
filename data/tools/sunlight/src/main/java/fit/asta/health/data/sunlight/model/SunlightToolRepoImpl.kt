package fit.asta.health.data.sunlight.model

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.sunlight.model.api.SunlightApi
import fit.asta.health.data.sunlight.model.network.response.SunlightToolData
import javax.inject.Inject


class SunlightToolRepoImpl
@Inject constructor(
    private val remoteApi: SunlightApi,
) : SunlightToolRepo {

    override suspend fun getSunlightTool(
        userId: String,
        latitude: String,
        longitude: String,
        date: String,
        location: String
    ): ResponseState<SunlightToolData> {
        return getApiResponseState {
            remoteApi.getSunlightTool(
                userId = userId,
                latitude = latitude,
                longitude = longitude,
                date = date,
                location = location
            )
        }
    }
}
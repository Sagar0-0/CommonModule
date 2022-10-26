package fit.asta.health.tools.water.model

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.Status
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.NetBeverage
import fit.asta.health.tools.water.model.network.NetBeverageRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WaterToolRepoImpl(
    private val remoteApi: RemoteApis,
    private val mapper: WaterToolDataMapper,
) : WaterToolRepo {

    override suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String
    ): Flow<WaterTool> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getWaterTool(
                        userId = userId,
                        latitude = latitude,
                        longitude = longitude,
                        location = location,
                        startDate = startDate,
                        endDate = endDate
                    )
                )
            )
        }
    }

    override suspend fun updateBeverage(beverage: NetBeverage): Flow<Status> {
        return flow {
            emit(
                remoteApi.updateBeverage(beverage)
            )
        }
    }

    override suspend fun updateBeverageQty(beverage: NetBeverage): Flow<Status> {
        return flow {
            emit(
                remoteApi.updateBeverageQty(beverage)
            )
        }
    }

    override suspend fun getBeverageList(userId: String): Flow<NetBeverageRes> {
        return flow {
            emit(
                remoteApi.getBeverageList(userId)
            )
        }
    }
}
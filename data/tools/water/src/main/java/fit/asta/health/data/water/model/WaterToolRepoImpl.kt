package fit.asta.health.data.water.model

import fit.asta.health.data.water.model.network.WaterDetailsData
import fit.asta.health.data.water.model.api.WaterApi
import fit.asta.health.data.water.model.domain.WaterTool
import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.network.data.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class WaterToolRepoImpl(
    private val remoteApi: WaterApi,
    private val mapper: WaterToolDataMapper,
) : WaterToolRepo {

    override suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: String
    ): Flow<WaterTool> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getWaterTool(
                        userId = userId,
                        latitude = latitude,
                        longitude = longitude,
                        location = location,
                        date = date
                    )
                )
            )
        }
    }

    override suspend fun updateBeverageQty(beverage: NetBevQtyPut): Flow<Status> {
        return flow { emit(remoteApi.updateBeverageQty(beverage)) }
    }

    override suspend fun updateWaterTool(waterToolData: WaterToolData): Flow<Status> {
        return flow { emit(remoteApi.updateWaterTool(waterToolData)) }
    }

    override suspend fun getWaterData() : Flow<List<WaterDetailsData>> {
        val result = remoteApi.getWaterData()
        return if (result.isSuccessful) {
            val response = result.body()
            flow {
                if (response != null) {
                    emit(response.data)
                }
            }
        } else {
            flow {
                emit(listOf())
            }
        }
    }

    /*override suspend fun updateWaterTool(modifiedWaterTool: ModifiedWaterTool):Flow<Status>{
           return flow {
               emit(
                   remoteApi.updateWaterTool(modifiedWaterTool)
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

       override suspend fun getBeverageList(userId: String): Flow<NetBeverageRes> {
           return flow {
               emit(
                   remoteApi.getBeverageList(userId)
               )
           }
       }*/
}
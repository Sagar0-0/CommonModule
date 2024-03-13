package fit.asta.health.data.water.model

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.water.model.api.WaterApi
import fit.asta.health.data.water.model.domain.WaterTool
import fit.asta.health.data.water.model.network.BeverageActivity
import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.WaterDetailsData
import fit.asta.health.data.water.model.network.WaterToolData
import fit.asta.health.network.data.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class WaterToolRepoImpl(
    private val remoteApi: WaterApi,
    private val mapper: WaterToolDataMapper,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : WaterToolRepo {

    override suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        date: Long
    ) = withContext(coroutineDispatcher) {
        getApiResponseState {
                        remoteApi.getWaterTool(
                            userId = userId,
                            latitude = latitude,
                            longitude = longitude,
                            location = location,
                            date = date
                        )
        }
    }

//        ResponseState<WaterTool> {
//            return flow {
//                emit(
//                    mapper.mapToDomainModel(
//                        remoteApi.getWaterTool(
//                            userId = userId,
//                            latitude = latitude,
//                            longitude = longitude,
//                            location = location,
//                            date = date
//                        )
//                    )
//                )
//            }
//        }

    override suspend fun updateBeverageQty(beverage: NetBevQtyPut)= withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.updateBeverageQty(beverage) }
    }


    override suspend fun updateWaterTool(waterToolData: WaterToolData) = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.updateWaterTool(waterToolData) }
    }

    override suspend fun getWaterData()= withContext(coroutineDispatcher) {
        getApiResponseState {  remoteApi.getWaterData() }
//        return flow {
//            emit(
//                remoteApi.getWaterData().data
//            )
//        }
//        val result = remoteApi.getWaterData()
//        return if (result.isSuccessful) {
//            val response = result.body()
//            flow {
//                if (response != null) {
//                    emit(response.data)
//                }
//            }
//        } else {
//            flow {
//                emit(listOf())
//            }
//        }
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
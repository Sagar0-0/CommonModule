package fit.asta.health.scheduler.model

import android.util.Log
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.Status
import fit.asta.health.scheduler.model.api.SchedulerApi
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.doman.mapToSchedulerGetData
import fit.asta.health.scheduler.model.doman.mapToSchedulerGetListData
import fit.asta.health.scheduler.model.doman.mapToSchedulerGetTagsList
import fit.asta.health.scheduler.model.doman.model.SchedulerGetData
import fit.asta.health.scheduler.model.doman.model.SchedulerGetListData
import fit.asta.health.scheduler.model.doman.model.SchedulerGetTagsList
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.scheduler.model.net.tag.ScheduleTagNetData
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class AlarmBackendRepoImp(
    private val remoteApi: SchedulerApi
) : AlarmBackendRepo {
    override suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): NetworkResult<AstaSchedulerPutResponse> {
        return try {
            NetworkResult.Loading<AstaSchedulerPutResponse>()
            val result = remoteApi.updateScheduleDataOnBackend(schedule)
            handleResponse(result)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    override suspend fun getScheduleDataFromBackend(scheduleId: String): Flow<NetworkResult<SchedulerGetData>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteApi.getScheduleDataFromBackend(scheduleId)
            if (result.isSuccessful) {
                emit(NetworkResult.Success(result.body()!!.mapToSchedulerGetData()))
            } else {
                NetworkResult.Error<SchedulerGetData>(message = result.message())
            }
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getScheduleListDataFromBackend(userId: String): Flow<NetworkResult<SchedulerGetListData>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteApi.getScheduleListDataFromBackend(userId)
            if (result.isSuccessful) {
                emit(NetworkResult.Success(result.body()!!.mapToSchedulerGetListData()))
            } else {
                NetworkResult.Error<SchedulerGetListData>(message = result.message())
            }
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteScheduleDataFromBackend(scheduleId: String): NetworkResult<AstaSchedulerDeleteResponse> {
        return try {
            NetworkResult.Loading<AstaSchedulerDeleteResponse>()
            val result = remoteApi.deleteScheduleDataFromBackend(scheduleId)
            handleResponse(result)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    override suspend fun getTagListFromBackend(userId: String): Flow<NetworkResult<SchedulerGetTagsList>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteApi.getTagListFromBackend(userId)
            Log.d("manish", "getTagListFromBackend: ${result.isSuccessful}")
            if (result.isSuccessful) {
                val data=result.body()!!.mapToSchedulerGetTagsList()
                emit(NetworkResult.Success(data))
                Log.d("manish", "getTagListFromBackend: ${data}")
            } else {
                NetworkResult.Error<SchedulerGetTagsList>(message = result.message())
            }
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateScheduleTag(schedule: ScheduleTagNetData): NetworkResult<Status> {
        return try {
            NetworkResult.Loading<Status>()
            val result = remoteApi.updateScheduleTag(schedule)
            NetworkResult.Success(result)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    override suspend fun getAllUserMedia(userId: String): Flow<NetworkResult<Status>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(NetworkResult.Success(remoteApi.getAllUserMedia(userId)))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateUserMedia(schedule: ScheduleTagNetData): NetworkResult<Status> {
        return try {
            NetworkResult.Loading<Status>()
            val result = remoteApi.updateUserMedia(schedule)
            NetworkResult.Success(result)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message)
        }
    }

    private fun <T : Any> handleResponse(response: Response<T>): NetworkResult<T> {
        Log.d(SpotifyConstants.TAG, "handleResponse: ${response.body()} ")
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Timeout!!\n $response"
                )
            }

            response.code() == 401 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Bad or expired token. This can happen if the user revoked a token or the access token has expired. You should re-authenticate the user.\n $response"
                )
            }

            response.code() == 403 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Bad OAuth request (wrong consumer key, bad nonce, expired timestamp...). Unfortunately, re-authenticating the user won't help here.\n $response"
                )
            }

            response.code() == 429 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "The app has exceeded its rate limits. $response"
                )
            }

            response.body() == null -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Empty Body. $response"
                )
            }

            response.isSuccessful -> {
                val result = response.body()
                return NetworkResult.Success(result!!)
            }

            response.code() == 200 -> {
                val result = response.body()
                return NetworkResult.Success(result!!)
            }

            else -> return NetworkResult.Error(
                message = response.body().toString(),
                data = response.body()
            )
        }
    }
}

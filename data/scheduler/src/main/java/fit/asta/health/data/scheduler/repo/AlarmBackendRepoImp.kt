package fit.asta.health.data.scheduler.repo

import android.content.Context
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.scheduler.local.model.AlarmEntity
import fit.asta.health.data.scheduler.remote.SchedulerApi
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.network.utils.InputStreamRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class AlarmBackendRepoImp
@Inject constructor(
    private val context: Context,
    private val remoteApi: SchedulerApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : AlarmBackendRepo {
    override suspend fun getTodayDataFromBackend(
        userID: String,
        date: Long,
        location: String,
        latitude: Float,
        longitude: Float
    ) = withContext(coroutineDispatcher) {
        getApiResponseState {
            remoteApi.getTodayDataFromBackend(
                userID,
                date,
                location,
                latitude,
                longitude
            )
        }
    }

    override suspend fun getDefaultSchedule(userID: String) = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getDefaultSchedule(userID) }
    }

    override suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity) =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.updateScheduleDataOnBackend(schedule)
            }
        }

    override suspend fun getScheduleDataFromBackend(scheduleId: String) =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.getScheduleDataFromBackend(scheduleId)
            }
        }

    override suspend fun getScheduleListDataFromBackend(userId: String) =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.getScheduleListDataFromBackend(userId)
            }
        }

    override suspend fun deleteScheduleDataFromBackend(scheduleId: String) =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.deleteScheduleDataFromBackend(scheduleId)
            }
        }

    override suspend fun getTagListFromBackend(userId: String) = withContext(coroutineDispatcher) {
        getApiResponseState {
            remoteApi.getTagListFromBackend(userId)
        }
    }

    override suspend fun updateScheduleTag(schedule: NetCustomTag) =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                val file = MultipartBody.Part.createFormData(
                    name = "file",
                    filename = schedule.name,
                    body = InputStreamRequestBody(context.contentResolver, schedule.localUrl!!)
                )
                remoteApi.updateScheduleTag(schedule, file)
            }
        }

    override suspend fun deleteTagFromBackend(userId: String, id: String) =
        withContext(coroutineDispatcher) {
            getApiResponseState { remoteApi.deleteTagFromBackend(userID = userId, id = id) }
        }
}

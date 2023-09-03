package fit.asta.health.data.scheduler.repo

import android.content.Context
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.SchedulerApiService
import fit.asta.health.data.scheduler.remote.getTodayData
import fit.asta.health.data.scheduler.remote.mapToSchedulerGetData
import fit.asta.health.data.scheduler.remote.mapToSchedulerGetListData
import fit.asta.health.data.scheduler.remote.mapToSchedulerGetTagsList
import fit.asta.health.data.scheduler.remote.model.SchedulerGetData
import fit.asta.health.data.scheduler.remote.model.SchedulerGetListData
import fit.asta.health.data.scheduler.remote.model.SchedulerGetTagsList
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.data.scheduler.remote.net.tag.ScheduleTagNetData
import fit.asta.health.network.data.Status
import fit.asta.health.network.utils.InputStreamRequestBody
import okhttp3.MultipartBody

class AlarmBackendRepoImp(
    private val context: Context,
    private val remoteApi: SchedulerApiService
) : AlarmBackendRepo {
    override suspend fun getTodayDataFromBackend(
        userID: String,
        date: String,
        location: String,
        latitude: Float,
        longitude: Float
    ): ResponseState<TodayData> {
        return getResponseState {
            remoteApi.getTodayDataFromBackend(userID, date, location, latitude, longitude)
                .getTodayData()
        }
    }

    override suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): ResponseState<AstaSchedulerPutResponse> {
        return getResponseState { remoteApi.updateScheduleDataOnBackend(schedule) }
    }

    override suspend fun getScheduleDataFromBackend(scheduleId: String): ResponseState<SchedulerGetData> {
        return getResponseState {
            remoteApi.getScheduleDataFromBackend(scheduleId).mapToSchedulerGetData()
        }
    }

    override suspend fun getScheduleListDataFromBackend(userId: String): ResponseState<SchedulerGetListData> {
        return getResponseState {
            remoteApi.getScheduleListDataFromBackend(userId).mapToSchedulerGetListData()
        }
    }

    override suspend fun deleteScheduleDataFromBackend(scheduleId: String): ResponseState<AstaSchedulerDeleteResponse> {
        return getResponseState { remoteApi.deleteScheduleDataFromBackend(scheduleId) }
    }

    override suspend fun getTagListFromBackend(userId: String): ResponseState<SchedulerGetTagsList> {
        return getResponseState {
            remoteApi.getTagListFromBackend(userId).mapToSchedulerGetTagsList()
        }
    }

    override suspend fun updateScheduleTag(schedule: ScheduleTagNetData): ResponseState<Status> {
        val file = MultipartBody.Part.createFormData(
            name = "file",
            filename = schedule.tag,
            body = InputStreamRequestBody(context.contentResolver, schedule.localUrl!!)
        )
        return getResponseState {
            remoteApi.updateScheduleTag(schedule, file)
        }
    }

    override suspend fun getAllUserMedia(userId: String): ResponseState<Status> {
        return getResponseState { remoteApi.getAllUserMedia(userId) }
    }

    override suspend fun updateUserMedia(schedule: ScheduleTagNetData): ResponseState<Status> {
        return getResponseState { remoteApi.updateUserMedia(schedule) }
    }

}

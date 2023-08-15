package fit.asta.health.scheduler.data.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.navigation.today.domain.mapper.getTodayData
import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.network.data.Status
import fit.asta.health.scheduler.data.api.SchedulerApiService
import fit.asta.health.scheduler.data.api.net.tag.ScheduleTagNetData
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.doman.mapToSchedulerGetData
import fit.asta.health.scheduler.doman.mapToSchedulerGetListData
import fit.asta.health.scheduler.doman.mapToSchedulerGetTagsList
import fit.asta.health.scheduler.doman.model.SchedulerGetData
import fit.asta.health.scheduler.doman.model.SchedulerGetListData
import fit.asta.health.scheduler.doman.model.SchedulerGetTagsList

class AlarmBackendRepoImp(
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

    override suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): ResponseState<fit.asta.health.scheduler.data.api.net.scheduler.AstaSchedulerPutResponse> {
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

    override suspend fun deleteScheduleDataFromBackend(scheduleId: String): ResponseState<fit.asta.health.scheduler.data.api.net.scheduler.AstaSchedulerDeleteResponse> {
        return getResponseState { remoteApi.deleteScheduleDataFromBackend(scheduleId) }
    }

    override suspend fun getTagListFromBackend(userId: String): ResponseState<SchedulerGetTagsList> {
        return getResponseState {
            remoteApi.getTagListFromBackend(userId).mapToSchedulerGetTagsList()
        }
    }

    override suspend fun updateScheduleTag(schedule: ScheduleTagNetData): ResponseState<Status> {
        return getResponseState { remoteApi.updateScheduleTag(schedule) }
    }

    override suspend fun getAllUserMedia(userId: String): ResponseState<Status> {
        return getResponseState { remoteApi.getAllUserMedia(userId) }
    }

    override suspend fun updateUserMedia(schedule: ScheduleTagNetData): ResponseState<Status> {
        return getResponseState { remoteApi.updateUserMedia(schedule) }
    }

}

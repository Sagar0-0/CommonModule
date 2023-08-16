package fit.asta.health.scheduler.data.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.network.data.Status
import fit.asta.health.scheduler.data.api.net.tag.ScheduleTagNetData
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.doman.model.SchedulerGetData
import fit.asta.health.scheduler.doman.model.SchedulerGetListData
import fit.asta.health.scheduler.doman.model.SchedulerGetTagsList

interface AlarmBackendRepo {

    //Scheduler
    suspend fun getTodayDataFromBackend(
        userID: String,
        date: String,
        location: String,
        latitude: Float,
        longitude: Float
    ): ResponseState<TodayData>

    suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): ResponseState<fit.asta.health.scheduler.data.api.net.scheduler.AstaSchedulerPutResponse>
    suspend fun getScheduleDataFromBackend(scheduleId: String): ResponseState<SchedulerGetData>
    suspend fun getScheduleListDataFromBackend(userId: String): ResponseState<SchedulerGetListData>
    suspend fun deleteScheduleDataFromBackend(scheduleId: String): ResponseState<fit.asta.health.scheduler.data.api.net.scheduler.AstaSchedulerDeleteResponse>

    //Tags
    suspend fun getTagListFromBackend(userId: String): ResponseState<SchedulerGetTagsList>
    suspend fun updateScheduleTag(schedule: ScheduleTagNetData): ResponseState<Status>

    //Media
    suspend fun getAllUserMedia(userId: String): ResponseState<Status>
    suspend fun updateUserMedia(schedule: ScheduleTagNetData): ResponseState<Status>
}
package fit.asta.health.data.scheduler.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.SchedulerGetData
import fit.asta.health.data.scheduler.remote.model.SchedulerGetListData
import fit.asta.health.data.scheduler.remote.model.SchedulerGetTagsList
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.network.data.Status

interface AlarmBackendRepo {

    //Scheduler
    suspend fun getTodayDataFromBackend(
        userID: String,
        date: String,
        location: String,
        latitude: Float,
        longitude: Float
    ): ResponseState<TodayData>

    suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): ResponseState<AstaSchedulerPutResponse>
    suspend fun getScheduleDataFromBackend(scheduleId: String): ResponseState<SchedulerGetData>
    suspend fun getScheduleListDataFromBackend(userId: String): ResponseState<SchedulerGetListData>
    suspend fun deleteScheduleDataFromBackend(scheduleId: String): ResponseState<AstaSchedulerDeleteResponse>

    //Tags
    suspend fun getTagListFromBackend(userId: String): ResponseState<SchedulerGetTagsList>
    suspend fun updateScheduleTag(schedule: NetCustomTag): ResponseState<Status>
    suspend fun deleteTagFromBackend(userId: String, id: String): ResponseState<Status>

}
package fit.asta.health.data.scheduler.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodayDefaultSchedule
import fit.asta.health.data.scheduler.remote.model.TodaySchedules
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.data.scheduler.remote.net.tag.TagsListResponse
import fit.asta.health.network.data.ServerRes

interface AlarmBackendRepo {

    //Scheduler
    suspend fun getTodayDataFromBackend(
        userID: String,
        date: String,
        location: String,
        latitude: Float,
        longitude: Float
    ): ResponseState<TodaySchedules>

    suspend fun getDefaultSchedule(userID: String): ResponseState<TodayDefaultSchedule>
    suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): ResponseState<ServerRes>
    suspend fun getScheduleDataFromBackend(scheduleId: String): ResponseState<AlarmEntity>
    suspend fun getScheduleListDataFromBackend(userId: String): ResponseState<List<AlarmEntity>>
    suspend fun deleteScheduleDataFromBackend(scheduleId: String): ResponseState<ServerRes>

    //Tags
    suspend fun getTagListFromBackend(userId: String): ResponseState<TagsListResponse>
    suspend fun updateScheduleTag(schedule: NetCustomTag): ResponseState<ServerRes>
    suspend fun deleteTagFromBackend(userId: String, id: String): ResponseState<ServerRes>

}
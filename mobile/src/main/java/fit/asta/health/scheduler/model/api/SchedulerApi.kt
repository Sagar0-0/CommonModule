package fit.asta.health.scheduler.model.api

import fit.asta.health.navigation.today.model.TodaySchedules
import fit.asta.health.network.data.Status
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.scheduler.model.net.tag.AstaGetTagsListResponse
import fit.asta.health.scheduler.model.net.tag.ScheduleTagNetData
import retrofit2.Response


//Scheduler Endpoints
interface SchedulerApi {

    //Scheduler
    suspend fun getTodayDataFromBackend(
        userID: String,
        date: String,
        location: String,
        latitude: Float,
        longitude: Float
    ): Response<TodaySchedules>

    suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): Response<AstaSchedulerPutResponse>
    suspend fun getScheduleDataFromBackend(scheduleId: String): Response<AstaSchedulerGetResponse>
    suspend fun getScheduleListDataFromBackend(userId: String): Response<AstaSchedulerGetListResponse>
    suspend fun deleteScheduleDataFromBackend(scheduleId: String): Response<AstaSchedulerDeleteResponse>

    //Tags
    suspend fun getTagListFromBackend(userId: String): Response<AstaGetTagsListResponse>
    suspend fun updateScheduleTag(schedule: ScheduleTagNetData): Status

    //Media
    suspend fun getAllUserMedia(userId: String): Status
    suspend fun updateUserMedia(schedule: ScheduleTagNetData): Status
}
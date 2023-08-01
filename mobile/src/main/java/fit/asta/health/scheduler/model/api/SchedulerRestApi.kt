package fit.asta.health.scheduler.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.navigation.today.model.TodaySchedules
import fit.asta.health.network.data.Status
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.scheduler.model.net.tag.AstaGetTagsListResponse
import fit.asta.health.scheduler.model.net.tag.ScheduleTagNetData
import okhttp3.OkHttpClient
import retrofit2.Response


//Scheduler Endpoints
class SchedulerRestApi(baseUrl: String, client: OkHttpClient) : SchedulerApi {

    private val apiService: SchedulerApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(SchedulerApiService::class.java)

    override suspend fun getTodayDataFromBackend(
        userID: String,
        date: String, location: String,
        latitude: Float,
        longitude: Float
    ): Response<TodaySchedules> {
        return apiService.getTodayDataFromBackend(userID, date, location, latitude, longitude)
    }

    //Scheduler
    override suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): Response<AstaSchedulerPutResponse> {
        return apiService.updateScheduleDataOnBackend(schedule)
    }

    override suspend fun getScheduleDataFromBackend(scheduleId: String): Response<AstaSchedulerGetResponse> {
        return apiService.getScheduleDataFromBackend(scheduleId)
    }

    override suspend fun getScheduleListDataFromBackend(userId: String): Response<AstaSchedulerGetListResponse> {
        return apiService.getScheduleListDataFromBackend(userId)
    }

    override suspend fun deleteScheduleDataFromBackend(scheduleId: String): Response<AstaSchedulerDeleteResponse> {
        return apiService.deleteScheduleDataFromBackend(scheduleId)
    }

    //Tags
    override suspend fun getTagListFromBackend(userId: String): Response<AstaGetTagsListResponse> {
        return apiService.getTagListFromBackend(userId)
    }

    override suspend fun updateScheduleTag(schedule: ScheduleTagNetData): Status {
        return apiService.updateScheduleTag(schedule)
    }

    //Media
    override suspend fun getAllUserMedia(userId: String): Status {
        return apiService.getAllUserMedia(userId)
    }

    override suspend fun updateUserMedia(schedule: ScheduleTagNetData): Status {
        return apiService.updateUserMedia(schedule)
    }
}
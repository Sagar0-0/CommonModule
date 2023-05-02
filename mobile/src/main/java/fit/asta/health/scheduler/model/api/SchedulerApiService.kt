package fit.asta.health.scheduler.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.scheduler.model.net.tag.AstaGetTagsListResponse
import fit.asta.health.scheduler.model.net.tag.ScheduleTagNetData
import retrofit2.Response
import retrofit2.http.*


//Scheduler Endpoints
interface SchedulerApiService {

    @PUT("schedule/put/")
    suspend fun updateScheduleDataOnBackend(
        @Body schedule: AlarmEntity
    ): Response<AstaSchedulerPutResponse>

    @GET("schedule/get/")
    suspend fun getScheduleDataFromBackend(
        @Query("sid") scheduleId: String
    ): Response<AstaSchedulerGetResponse>

    @GET("schedule/healthHisList/get/")
    suspend fun getScheduleListDataFromBackend(
        @Query("uid") userID: String
    ): Response<AstaSchedulerGetListResponse>

    @DELETE("schedule/delete/")
    suspend fun deleteScheduleDataFromBackend(
        @Query("sid") scheduleID: String
    ): Response<AstaSchedulerDeleteResponse>

    // Tags Endpoints
    @GET("tag/healthHisList/get/")
    suspend fun getTagListFromBackend(
        @Query("uid") userID: String
    ): Response<AstaGetTagsListResponse>

    @PUT("tag/put")
    suspend fun updateScheduleTag(@Body schedule: ScheduleTagNetData): Status

    // Media Endpoints
    @GET("sound/healthHisList/get/?")
    suspend fun getAllUserMedia(@Query("uid") userID: String): Status

    @PUT("sound/put")
    suspend fun updateUserMedia(@Body schedule: ScheduleTagNetData): Status
}
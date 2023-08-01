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
import retrofit2.http.*


//Scheduler Endpoints
interface SchedulerApiService {

    @GET("schedule/home/get/?")// http://asta.fit/schedule/home/get/?uid=6309a9379af54f142c65fbfe&lat=28.6353&lon=77.2250&date=2023-07-03&loc=bangalore
    suspend fun getTodayDataFromBackend(
        @Query("uid") userID: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Response<TodaySchedules>

    @PUT("schedule/put/")
    suspend fun updateScheduleDataOnBackend(
        @Body schedule: AlarmEntity
    ): Response<AstaSchedulerPutResponse>

    @GET("schedule/get/")
    suspend fun getScheduleDataFromBackend(
        @Query("sid") scheduleId: String
    ): Response<AstaSchedulerGetResponse>

    @GET("schedule/list/get/?")
    suspend fun getScheduleListDataFromBackend(
        @Query("uid") userID: String
    ): Response<AstaSchedulerGetListResponse>

    @DELETE("schedule/delete/")
    suspend fun deleteScheduleDataFromBackend(
        @Query("sid") scheduleID: String
    ): Response<AstaSchedulerDeleteResponse>

    // Tags Endpoints
    @GET("tag/list/get/?")//https://asta.fit/tag/list/get/?uid=6309a9379af54f142c65fbff
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
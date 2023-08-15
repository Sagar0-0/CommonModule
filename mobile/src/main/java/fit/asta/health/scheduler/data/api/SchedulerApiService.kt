package fit.asta.health.scheduler.data.api

import fit.asta.health.navigation.today.data.model.TodaySchedules
import fit.asta.health.network.data.Status
import fit.asta.health.scheduler.data.api.net.scheduler.*
import fit.asta.health.scheduler.data.api.net.tag.*
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
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
    ): TodaySchedules

    @PUT("schedule/put/")
    suspend fun updateScheduleDataOnBackend(
        @Body schedule: AlarmEntity
    ): AstaSchedulerPutResponse

    @GET("schedule/get/")
    suspend fun getScheduleDataFromBackend(
        @Query("sid") scheduleId: String
    ): AstaSchedulerGetResponse

    @GET("schedule/list/get/?")
    suspend fun getScheduleListDataFromBackend(
        @Query("uid") userID: String
    ): AstaSchedulerGetListResponse

    @DELETE("schedule/delete/")
    suspend fun deleteScheduleDataFromBackend(
        @Query("sid") scheduleID: String
    ): AstaSchedulerDeleteResponse

    // Tags Endpoints
    @GET("tag/list/get/?")//https://asta.fit/tag/list/get/?uid=6309a9379af54f142c65fbff
    suspend fun getTagListFromBackend(
        @Query("uid") userID: String
    ): AstaGetTagsListResponse

    @PUT("tag/put")
    suspend fun updateScheduleTag(@Body schedule: ScheduleTagNetData): Status

    // Media Endpoints
    @GET("sound/healthHisList/get/?")
    suspend fun getAllUserMedia(@Query("uid") userID: String): Status

    @PUT("sound/put")
    suspend fun updateUserMedia(@Body schedule: ScheduleTagNetData): Status
}
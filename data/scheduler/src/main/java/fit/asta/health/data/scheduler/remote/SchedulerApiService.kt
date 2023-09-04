package fit.asta.health.data.scheduler.remote

import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodaySchedules
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.data.scheduler.remote.net.tag.AstaGetTagsListResponse
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.network.data.Status
import okhttp3.MultipartBody
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
    @GET("tag/list/get/?")//https://asta.fit/tag/list/get/?uid=6309a9379af54f142c65fbfe
    suspend fun getTagListFromBackend(
        @Query("uid") userID: String
    ): AstaGetTagsListResponse

    @PUT("tag/put")
    @Multipart
    suspend fun updateScheduleTag(
        @Part("json") schedule: NetCustomTag,
        @Part file: MultipartBody.Part,
    ): Status

    @DELETE("tag/delete/")
    suspend fun deleteTagFromBackend(
        @Query("uid") userID: String,
        @Query("tid") id: String
    ): Status
}
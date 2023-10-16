package fit.asta.health.data.scheduler.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodayDefaultSchedule
import fit.asta.health.data.scheduler.remote.model.TodaySchedules
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.data.scheduler.remote.net.tag.TagsListResponse
import fit.asta.health.network.data.ServerRes
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
    ): Response<TodaySchedules>

    @GET("schedule/home/today/events/get/?")
    suspend fun getDefaultSchedule(
        @Query("uid") userID: String
    ): Response<TodayDefaultSchedule>

    @PUT("schedule/put/")
    suspend fun updateScheduleDataOnBackend(
        @Body schedule: AlarmEntity
    ): Response<ServerRes>

    @GET("schedule/get/")
    suspend fun getScheduleDataFromBackend(
        @Query("sid") scheduleId: String
    ): Response<AlarmEntity>

    @GET("schedule/list/get/?")
    suspend fun getScheduleListDataFromBackend(
        @Query("uid") userID: String
    ): Response<List<AlarmEntity>>

    @DELETE("schedule/delete/")
    suspend fun deleteScheduleDataFromBackend(
        @Query("sid") scheduleID: String
    ): Response<ServerRes>

    // Tags Endpoints
    @GET("tag/list/get/?")//https://asta.fit/tag/list/get/?uid=6309a9379af54f142c65fbfe
    suspend fun getTagListFromBackend(
        @Query("uid") userID: String
    ): Response<TagsListResponse>

    @PUT("tag/put")
    @Multipart
    suspend fun updateScheduleTag(
        @Part("json") schedule: NetCustomTag,
        @Part file: MultipartBody.Part,
    ): Response<ServerRes>

    @DELETE("tag/delete/")
    suspend fun deleteTagFromBackend(
        @Query("uid") userID: String,
        @Query("tid") id: String
    ): Response<ServerRes>
}
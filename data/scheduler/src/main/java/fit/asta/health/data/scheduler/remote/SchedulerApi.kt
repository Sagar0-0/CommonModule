package fit.asta.health.data.scheduler.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.scheduler.local.model.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodaySchedules
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.data.scheduler.remote.net.tag.TagsListResponse
import fit.asta.health.network.data.ServerRes
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query


//Scheduler Endpoints
interface SchedulerApi {

    @GET("schedule/home/get/?")
    suspend fun getTodayDataFromBackend(
        @Query("uid") userID: String,
        @Query("date") date: Long,
        @Query("loc") location: String,
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Response<TodaySchedules>

    @GET("schedule/home/today/events/get/?")
    suspend fun getDefaultSchedule(
        @Query("uid") userID: String
    ): Response<List<AlarmEntity>>

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
    @GET("tag/list/get/?")
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
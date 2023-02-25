package fit.asta.health.network.api

import fit.asta.health.common.multiselect.data.UserInputs
import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
import fit.asta.health.network.data.MultiFileUploadRes
import fit.asta.health.network.data.SingleFileUploadRes
import fit.asta.health.network.data.Status
import fit.asta.health.network.data.UploadInfo
import fit.asta.health.old.course.details.networkdata.CourseDetailsResponse
import fit.asta.health.old.course.listing.networkdata.CoursesListNetData
import fit.asta.health.old.course.session.networkdata.SessionResponse
import fit.asta.health.old.scheduler.networkdata.ScheduleNetData
import fit.asta.health.old.scheduler.networkdata.ScheduleResponse
import fit.asta.health.old.scheduler.tags.networkdata.ScheduleTagNetData
import fit.asta.health.old.scheduler.tags.networkdata.ScheduleTagResponse
import fit.asta.health.old.scheduler.tags.networkdata.ScheduleTagsResponse
import fit.asta.health.old.subscription.networkdata.SubscriptionDataResponse
import fit.asta.health.old.subscription.networkdata.SubscriptionStatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {

    //Home page
    @GET("home/get?")
    suspend fun getHomeData(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("loc") location: String,
        @Query("start") startDate: String,
        @Query("end") endDate: String,
        @Query("time") time: String
    ): NetHealthToolsRes

    @PUT("tool/selected/put")
    suspend fun updateSelectedTools(@Body toolIds: NetSelectedTools): Status

    //File upload Endpoints ------------------------------------------------------------------------
    @Multipart
    @PUT("file/upload/put/")
    suspend fun uploadFile(
        @Part("json") info: UploadInfo,
        @Part file: MultipartBody.Part
    ): SingleFileUploadRes

    @Multipart
    @PUT("file/upload/put/")
    suspend fun uploadFile(
        @Part("json") info: UploadInfo,
        @Part file: MultipartBody.Part,
        @Tag progressCallback: ProgressCallback?
    ): SingleFileUploadRes

    @Multipart
    @PUT("file/upload/list/put/")
    suspend fun uploadFiles(
        @Body body: RequestBody?,
        @Part file: MultipartBody
    ): MultiFileUploadRes

    @Multipart
    @PUT("file/upload/list/put/")
    suspend fun uploadFiles(
        @Body body: RequestBody?,
        @Part file: MultipartBody,
        @Tag progressCallback: ProgressCallback?
    ): MultiFileUploadRes

    @DELETE("file/upload/delete/")
    suspend fun deleteFile(@Query("uid") Id: String, @Query("feature") feature: String): Status

    @DELETE("file/upload/list/delete/")
    suspend fun deleteFiles(@Query("uid") Id: String, @Query("feature") feature: String): Status

    //Old APIs ------------------------------------------------------------------------------------
    @GET("course/list/get")
    suspend fun getCoursesList(
        @Query("catId") categoryId: String,
        @Query("index") index: Int,
        @Query("limit") limit: Int
    ): CoursesListNetData

    @GET("course/details/get")
    suspend fun getCourseDetails(@Query("courseId") courseId: String): CourseDetailsResponse

    @GET("course/session/get")
    suspend fun getSession(
        @Query("userId") userId: String,
        @Query("courseId") courseId: String,
        @Query("sessionId") sessionId: String
    ): SessionResponse

    @GET("subscription/plan/get")
    suspend fun getSubscriptionPlans(): SubscriptionDataResponse

    @GET("subscription/statusDTO/get")
    suspend fun getSubscriptionStatus(@Query("userId") userId: String): SubscriptionStatusResponse

    /*
    @GET("offer/list/get")
    suspend fun getOffer(@Query("userId") userId: String): OfferNetData

    @GET("referral/get")
    suspend fun getReferralInfo(@Query("userId") userId: String): OfferNetData
     */

    @GET("user/profile/data/get")
    suspend fun getMultiSelectionData(@Query("uid") uid: String): UserInputs

    @POST("schedule/tag/post")
    suspend fun postScheduleTag(@Body schedule: ScheduleTagNetData): Status

    @GET("schedule/tag/get")
    suspend fun getScheduleTag(
        @Query("userId") userId: String,
        @Query("tagId") tagId: String
    ): ScheduleTagResponse

    @PUT("schedule/tag/put")
    suspend fun putScheduleTag(@Body schedule: ScheduleTagNetData): Status

    @GET("schedule/tag/list/get")
    suspend fun getScheduleTagList(): ScheduleTagsResponse

    @POST("schedule/activity/post")
    suspend fun postScheduledPlan(@Body schedule: ScheduleNetData)

    @GET("schedule/activity/get")
    suspend fun getScheduledPlan(
        @Query("userId") userId: String,
        @Query("scheduleId") scheduleId: String
    ): ScheduleResponse

    @PUT("schedule/activity/put")
    suspend fun putRescheduledPlan(@Body schedule: ScheduleNetData)

    @GET("schedule/plan/list/get")
    suspend fun getTodayPlan(@Query("userId") userId: String): TodayPlanNetData
}

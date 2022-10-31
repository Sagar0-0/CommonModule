package fit.asta.health.network.api

import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.navigation.home_old.banners.networkdata.BannerResponse
import fit.asta.health.navigation.home_old.categories.networkdata.CategoriesNetData
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
import fit.asta.health.network.data.Status
import fit.asta.health.old_course.details.networkdata.CourseDetailsResponse
import fit.asta.health.old_course.listing.networkdata.CoursesListNetData
import fit.asta.health.old_course.session.networkdata.SessionResponse
import fit.asta.health.old_profile.data.chips.UserInputs
import fit.asta.health.old_profile.data.userprofile.Data
import fit.asta.health.old_profile.data.userprofile.UserProfile
import fit.asta.health.old_scheduler.networkdata.ScheduleNetData
import fit.asta.health.old_scheduler.networkdata.ScheduleResponse
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagNetData
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagResponse
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagsResponse
import fit.asta.health.old_subscription.networkdata.SubscriptionDataResponse
import fit.asta.health.old_subscription.networkdata.SubscriptionStatusResponse
import fit.asta.health.old_testimonials.networkdata.TestimonialListResponse
import fit.asta.health.old_testimonials.networkdata.TestimonialNetData
import fit.asta.health.old_testimonials.networkdata.TestimonialResponse
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import fit.asta.health.profile.model.network.NetUserProfile
import fit.asta.health.profile.model.network.NetUserProfileAvailableRes
import fit.asta.health.profile.model.network.NetUserProfileRes
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes
import fit.asta.health.tools.water.model.network.NetBeverage
import fit.asta.health.tools.water.model.network.NetBeverageRes
import fit.asta.health.tools.water.model.network.NetWaterToolRes
import retrofit2.http.*


interface ApiService {

    //User Profile
    @GET("userProfile/get/isUserProfileAvailable/?")
    suspend fun isUserProfileAvailable(@Query("uid") userId: String): NetUserProfileAvailableRes

    @PUT("user/profile/put")
    suspend fun updateUserProfile(@Body netUserProfile: NetUserProfile): Status

    @GET("userProfile/get/?")
    suspend fun getUserProfile(@Query("uid") userId: String): NetUserProfileRes

    @GET("health/property/getall/?")
    suspend fun getHealthProperties(@Query("property") propertyType: String): NetHealthPropertiesRes

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

    /*
    // Scheduler Endpoints
    @PUT("schedule/put/")
    suspend fun updateScheduleDataOnBackend(
        @Body schedule: AlarmEntity
    ): Response<AstaSchedulerPutResponse>

    @GET("schedule/get/")
    suspend fun getScheduleDataFromBackend(
        @Query("sid") scheduleId: String
    ): Response<AstaSchedulerGetResponse>

    @GET("schedule/list/get/")
    suspend fun getScheduleListDataFromBackend(
        @Query("uid") userID: String
    ): Response<AstaSchedulerGetListResponse>

    @DELETE("schedule/delete/")
    suspend fun deleteScheduleFromBackend(
        @Query("sid") scheduleID: String
    ): Response<AstaSchedulerDeleteResponse>

    // Tags Endpoints
    @GET("tag/list/get/")
    suspend fun getTagListDataFromBackend(
        @Query("uid") userID: String
    ): Response<AstaGetTagsListResponse>

    @PUT("tag/put")
    suspend fun updateScheduleTag(@Body schedule: ScheduleTagNetData): Status

    // Media Endpoints
    @GET("sound/list/get/?")
    suspend fun getAllUserMedia(@Query("uid") userID: String): Status

    @PUT("sound/put")
    suspend fun updateUserMedia(@Body schedule: ScheduleTagNetData): Status
     */

    //Health Tool - Water Endpoints
    @GET("tool/water/get/?")
    suspend fun getWaterTool(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("loc") location: String,
        @Query("start") startDate: String,
        @Query("end") endDate: String
    ): NetWaterToolRes

    @PUT("tool/water/beverage/add/put")
    suspend fun updateBeverage(@Body beverage: NetBeverage): Status

    @POST("tool/water/beverage/quantity/post")
    suspend fun updateBeverageQty(@Body beverage: NetBeverage): Status

    @GET("tool/water/beverage/list/get/?")
    suspend fun getBeverageList(@Query("uid") userId: String): NetBeverageRes

    //Health Tool - Sunlight Endpoints
    @GET("tool/sunlight/get")
    suspend fun getSunlightTool(@Query("userId") userId: String): NetSunlightToolRes

    //Health Tool - Walking Endpoints
    @GET("tool/walking/get")
    suspend fun getWalkingTool(@Query("userId") userId: String): NetWalkingToolRes

    //Testimonial Endpoints
    @GET("testimonial/list/get?")
    suspend fun getTestimonials(
        @Query("limit") limit: Int,
        @Query("index") index: Int
    ): NetTestimonialsRes

    @PUT("testimonial/put/")
    suspend fun updateTestimonial(@Body netTestimonial: NetTestimonial): Status

    @GET("testimonial/get/?")
    suspend fun getUserTestimonial(@Query("uid") userId: String): NetTestimonialRes

    //Feedback Endpoints
    @GET("feedback/user/get/?")
    suspend fun getFeedbackQuestions(
        @Query("uid") userId: String,
        @Query("fid") featureId: String
    ): NetFeedbackRes

    @POST("feedback/user/post")
    suspend fun postUserFeedback(@Body feedback: NetUserFeedback): Status

    //Old APIs ------------------------------------------------------------------------------------
    @GET("banner/list/get")
    suspend fun getBanners(@Query("type") type: String): BannerResponse

    @GET("category/list/get")
    suspend fun getCategories(@Query("pid") type: String): CategoriesNetData

    @GET("course/list/get")
    suspend fun getCoursesList(
        @Query("catId") categoryId: String,
        @Query("limit") limit: Int,
        @Query("index") index: Int
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

    @POST("testimonial/post")
    suspend fun postTestimonial(@Body testimonial: TestimonialNetData): Status

    @PUT("testimonial/post")
    suspend fun putTestimonial(@Body testimonial: TestimonialNetData): Status

    @GET("testimonial/list/get?")
    suspend fun getTestimonialList(
        @Query("limit") limit: Int,
        @Query("index") index: Int
    ): TestimonialListResponse

    @GET("testimonial/get/?")
    suspend fun getTestimonial(@Query("uid") userId: String): TestimonialResponse

    @GET("user/profile/get")
    suspend fun getProfile(@Query("userId") userId: String): UserProfile

    @GET("user/profile/data/get")
    suspend fun getMultiSelectionData(@Query("uid") uid: String): UserInputs

    @POST("user/profile/post")
    suspend fun postProfile(@Body body: Data)

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

package fit.asta.health.network.api

import fit.asta.health.course.details.networkdata.CourseDetailsResponse
import fit.asta.health.course.listing.networkdata.CoursesListNetData
import fit.asta.health.course.session.networkdata.SessionResponse
import fit.asta.health.feedback.model.network.response.NetFeedbackRes
import fit.asta.health.navigation.home.model.network.response.NetHealthToolsRes
import fit.asta.health.navigation.home_old.banners.networkdata.BannerResponse
import fit.asta.health.navigation.home_old.categories.networkdata.CategoriesNetData
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
import fit.asta.health.network.data.Status
import fit.asta.health.old_profile.data.chips.UserInputs
import fit.asta.health.old_profile.data.userprofile.Data
import fit.asta.health.old_profile.data.userprofile.UserProfile
import fit.asta.health.old_scheduler.networkdata.ScheduleNetData
import fit.asta.health.old_scheduler.networkdata.ScheduleResponse
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagNetData
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagResponse
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagsResponse
import fit.asta.health.old_testimonials.networkdata.TestimonialListResponse
import fit.asta.health.old_testimonials.networkdata.TestimonialNetData
import fit.asta.health.old_testimonials.networkdata.TestimonialResponse
import fit.asta.health.profile.model.network.NetUserProfileRes
import fit.asta.health.subscription.networkdata.SubscriptionDataResponse
import fit.asta.health.subscription.networkdata.SubscriptionStatusResponse
import fit.asta.health.testimonials.model.network.response.NetTestimonialRes
import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import fit.asta.health.tools.water.model.network.response.NetWaterToolRes
import retrofit2.http.*


interface ApiService {

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

    @GET("testimonial/list/get?")
    suspend fun getTestimonials(
        @Query("limit") limit: Int,
        @Query("index") index: Int
    ): NetTestimonialRes

    @POST("testimonial/post")
    suspend fun postTestimonial(@Body testimonial: TestimonialNetData): Status

    @GET("testimonial/get?")
    suspend fun getTestimonial(
        @Query("userId") userId: String
    ): TestimonialResponse

    @PUT("testimonial/put")
    suspend fun updateTestimonial(@Body testimonial: TestimonialNetData): Status

    @GET("testimonial/list/get?")
    suspend fun getTestimonialList(
        @Query("limit") limit: Int,
        @Query("index") index: Int
    ): TestimonialListResponse

    @GET("user/profile/get")
    suspend fun getProfile(@Query("userId") userId: String): UserProfile

    @GET("userProfile/get/?")
    suspend fun getUserProfile(@Query("uid") uid: String): NetUserProfileRes

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

    @GET("tool/sunlight/get")
    suspend fun getSunlightTool(@Query("userId") userId: String): NetSunlightToolRes

    @GET("tool/water/get")
    suspend fun getWaterTool(@Query("userId") userId: String): NetWaterToolRes

    @GET("feedback/get?")
    suspend fun getFeedback(@Query("userId") userId: String): NetFeedbackRes

    /*
    @POST("user/preference/favourite")
    suspend fun postUserPreferenceFavourite(@Body body: FavouriteItem)

    @POST("user/activity/progress")
    suspend fun postActivityProgress(@Body body: ActivityProgress)

    @GET("user/preferences")
    suspend fun getUserPreferences(@Query("userId") userId: String): UserProfile

    @GET("user/track/list")
    suspend fun getTrackInfo(@Query("userId") userId: String): UserProfile

    @GET("user/track/activity")
    suspend fun getTrackActivity(@Query("userId") userId: String): UserProfile
    */
}

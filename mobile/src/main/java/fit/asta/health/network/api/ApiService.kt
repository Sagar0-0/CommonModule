package fit.asta.health.network.api

import fit.asta.health.course.details.networkdata.CourseDetailsResponse
import fit.asta.health.course.listing.networkdata.CoursesListNetData
import fit.asta.health.course.session.networkdata.SessionResponse
import fit.asta.health.navigation.home_old.banners.networkdata.BannerResponse
import fit.asta.health.navigation.home_old.categories.networkdata.CategoriesNetData
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
import fit.asta.health.network.data.Status
import fit.asta.health.profile.data.chips.UserInputs
import fit.asta.health.profile.data.userprofile.Data
import fit.asta.health.profile.data.userprofile.UserProfile
import fit.asta.health.schedule.networkdata.ScheduleNetData
import fit.asta.health.schedule.networkdata.ScheduleResponse
import fit.asta.health.schedule.tags.networkdata.ScheduleTagNetData
import fit.asta.health.schedule.tags.networkdata.ScheduleTagResponse
import fit.asta.health.schedule.tags.networkdata.ScheduleTagsResponse
import fit.asta.health.subscription.networkdata.SubscriptionDataResponse
import fit.asta.health.subscription.networkdata.SubscriptionStatusResponse
import fit.asta.health.testimonials.networkdata.TestimonialListResponse
import fit.asta.health.testimonials.networkdata.TestimonialNetData
import fit.asta.health.testimonials.networkdata.TestimonialResponse
import retrofit2.http.*


interface ApiService {

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

    @GET("subscription/status/get")
    suspend fun getSubscriptionStatus(@Query("userId") userId: String): SubscriptionStatusResponse

    /*@GET("offer/list/get")
    suspend fun getOffer(@Query("userId") userId: String): OfferNetData

    @GET("referral/get")
    suspend fun getReferralInfo(@Query("userId") userId: String): OfferNetData
     */

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

    /*@POST("user/preference/favourite")
    suspend fun postUserPreferenceFavourite(@Body body: FavouriteItem)

    @POST("user/activity/progress")
    suspend fun postActivityProgress(@Body body: ActivityProgress)

    @GET("user/preferences")
    suspend fun getUserPreferences(@Query("userId") userId: String): UserProfile

    @GET("user/track/list")
    suspend fun getTrackInfo(@Query("userId") userId: String): UserProfile

    @GET("user/track/activity")
    suspend fun getTrackActivity(@Query("userId") userId: String): UserProfile*/
}

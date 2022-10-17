package fit.asta.health.network.api

import fit.asta.health.course.details.networkdata.CourseDetailsResponse
import fit.asta.health.course.listing.networkdata.CoursesListNetData
import fit.asta.health.course.session.networkdata.SessionResponse
import fit.asta.health.feedback.model.network.response.NetFeedbackRes
import fit.asta.health.navigation.home.model.network.response.HealthTools
import fit.asta.health.navigation.home_old.banners.networkdata.BannerResponse
import fit.asta.health.navigation.home_old.categories.networkdata.CategoriesNetData
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
import fit.asta.health.network.data.Status
import fit.asta.health.old_profile.data.chips.UserInputs
import fit.asta.health.old_profile.data.userprofile.Data
import fit.asta.health.old_profile.data.userprofile.UserProfile
import fit.asta.health.old_testimonials.networkdata.TestimonialListResponse
import fit.asta.health.old_testimonials.networkdata.TestimonialNetData
import fit.asta.health.old_testimonials.networkdata.TestimonialResponse
import fit.asta.health.profile.model.network.NetUserProfileRes
import fit.asta.health.schedule.networkdata.ScheduleNetData
import fit.asta.health.schedule.networkdata.ScheduleResponse
import fit.asta.health.schedule.tags.networkdata.ScheduleTagNetData
import fit.asta.health.schedule.tags.networkdata.ScheduleTagResponse
import fit.asta.health.schedule.tags.networkdata.ScheduleTagsResponse
import fit.asta.health.subscription.networkdata.SubscriptionDataResponse
import fit.asta.health.subscription.networkdata.SubscriptionStatusResponse
import fit.asta.health.testimonials.model.network.response.NetTestimonialRes
import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import fit.asta.health.tools.water.model.network.response.NetWaterToolRes

interface RemoteApis {

    suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): HealthTools

    suspend fun getTestimonials(limit: Int, index: Int): NetTestimonialRes
    suspend fun getBanners(type: String): BannerResponse
    suspend fun getCategories(type: String): CategoriesNetData
    suspend fun getCoursesList(categoryId: String, limit: Int, index: Int): CoursesListNetData
    suspend fun getCourseDetails(courseId: String): CourseDetailsResponse
    suspend fun getSubscriptionPlans(): SubscriptionDataResponse
    suspend fun getSubscriptionStatus(userId: String): SubscriptionStatusResponse
    suspend fun getSession(userId: String, courseId: String, sessionId: String): SessionResponse
    suspend fun postTestimonial(testimonial: TestimonialNetData): Status
    suspend fun getTestimonial(userId: String): TestimonialResponse
    suspend fun putTestimonial(testimonial: TestimonialNetData): Status
    suspend fun getTestimonialList(limit: Int, index: Int): TestimonialListResponse
    suspend fun getProfile(userId: String): UserProfile
    suspend fun getUserProfile(userId: String): NetUserProfileRes
    suspend fun getMultiSelectionData(uid: String): UserInputs
    suspend fun postProfile(data: Data)

    suspend fun getScheduledPlan(userId: String, scheduleId: String): ScheduleResponse
    suspend fun postScheduledPlan(scheduleData: ScheduleNetData)
    suspend fun putRescheduledPlan(scheduleData: ScheduleNetData)
    suspend fun postScheduleTag(schedule: ScheduleTagNetData): Status
    suspend fun getScheduleTag(userId: String, tagId: String): ScheduleTagResponse
    suspend fun putScheduleTag(schedule: ScheduleTagNetData): Status
    suspend fun getScheduleTagList(): ScheduleTagsResponse
    suspend fun getTodayPlan(userId: String): TodayPlanNetData

    //Health Tools
    suspend fun getSunlightTool(userId: String): NetSunlightToolRes
    suspend fun getWaterTool(userId: String): NetWaterToolRes

    //Feedback
    suspend fun getFeedback(userId: String): NetFeedbackRes
}
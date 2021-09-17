package fit.asta.health.network.api

import fit.asta.health.course.details.networkdata.CourseDetailsResponse
import fit.asta.health.course.listing.networkdata.CoursesListNetData
import fit.asta.health.course.session.networkdata.SessionResponse
import fit.asta.health.navigation.home.banners.networkdata.BannerResponse
import fit.asta.health.navigation.home.categories.networkdata.CategoriesNetData
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

interface RemoteApis {
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
}
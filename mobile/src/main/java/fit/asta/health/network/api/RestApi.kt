package fit.asta.health.network.api

import fit.asta.health.course.details.networkdata.CourseDetailsResponse
import fit.asta.health.course.listing.networkdata.CoursesListNetData
import fit.asta.health.course.session.networkdata.SessionResponse
import fit.asta.health.navigation.home.model.network.response.HealthTools
import fit.asta.health.navigation.home_old.banners.networkdata.BannerResponse
import fit.asta.health.navigation.home_old.categories.networkdata.CategoriesNetData
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.network.UserProfileResponse
import fit.asta.health.profile_old.data.chips.UserInputs
import fit.asta.health.profile_old.data.userprofile.Data
import fit.asta.health.profile_old.data.userprofile.UserProfile
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
import fit.asta.health.utils.NetworkUtil
import okhttp3.OkHttpClient

class RestApi(baseUrl: String, client: OkHttpClient) :
    RemoteApis {

    private val apiService: ApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(ApiService::class.java)

    override suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): HealthTools {
        return apiService.getHomeData(
            userId,
            latitude,
            longitude,
            location,
            startDate,
            endDate,
            time
        )
    }

    override suspend fun getBanners(type: String): BannerResponse {
        return apiService.getBanners(type)
    }

    override suspend fun getCategories(type: String): CategoriesNetData {
        return apiService.getCategories(type)
    }

    override suspend fun getCourseDetails(courseId: String): CourseDetailsResponse {
        return apiService.getCourseDetails(courseId)
    }

    override suspend fun getCoursesList(
        categoryId: String,
        limit: Int,
        index: Int
    ): CoursesListNetData {
        return apiService.getCoursesList(categoryId, limit, index)
    }

    override suspend fun getTodayPlan(userId: String): TodayPlanNetData {
        return apiService.getTodayPlan(userId)
    }

    override suspend fun postTestimonial(testimonial: TestimonialNetData): Status {
        return apiService.postTestimonial(testimonial)
    }

    override suspend fun getTestimonial(userId: String): TestimonialResponse {
        return apiService.getTestimonial(userId)
    }

    override suspend fun putTestimonial(testimonial: TestimonialNetData): Status {
        return apiService.updateTestimonial(testimonial)
    }

    override suspend fun getTestimonialList(limit: Int, index: Int): TestimonialListResponse {
        return apiService.getTestimonialList(limit, index)
    }

    override suspend fun getSubscriptionPlans(): SubscriptionDataResponse {
        return apiService.getSubscriptionPlans()
    }

    override suspend fun getSubscriptionStatus(userId: String): SubscriptionStatusResponse {
        return apiService.getSubscriptionStatus(userId)
    }

    override suspend fun getSession(
        userId: String,
        courseId: String,
        sessionId: String
    ): SessionResponse {
        return apiService.getSession(userId, courseId, sessionId)
    }

    override suspend fun getProfile(userId: String): UserProfile {
        return apiService.getProfile(userId)
    }

    override suspend fun getUserProfile(userId: String): UserProfileResponse {
        return apiService.getUserProfile(userId)
    }

    override suspend fun getMultiSelectionData(uid: String): UserInputs {
        return apiService.getMultiSelectionData(uid)
    }

    override suspend fun postProfile(data: Data) {
        return apiService.postProfile(data)
    }

    override suspend fun getScheduledPlan(userId: String, scheduleId: String): ScheduleResponse {
        return apiService.getScheduledPlan(userId, scheduleId)
    }

    override suspend fun postScheduledPlan(scheduleData: ScheduleNetData) {
        return apiService.postScheduledPlan(scheduleData)
    }

    override suspend fun putRescheduledPlan(scheduleData: ScheduleNetData) {
        return apiService.putRescheduledPlan(scheduleData)
    }

    override suspend fun postScheduleTag(schedule: ScheduleTagNetData): Status {
        return apiService.postScheduleTag(schedule)
    }

    override suspend fun getScheduleTag(userId: String, tagId: String): ScheduleTagResponse {
        return apiService.getScheduleTag(userId, tagId)
    }

    override suspend fun putScheduleTag(schedule: ScheduleTagNetData): Status {
        return apiService.putScheduleTag(schedule)
    }

    override suspend fun getScheduleTagList(): ScheduleTagsResponse {
        return apiService.getScheduleTagList()
    }
}
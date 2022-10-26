package fit.asta.health.network.api

import fit.asta.health.course.details.networkdata.CourseDetailsResponse
import fit.asta.health.course.listing.networkdata.CoursesListNetData
import fit.asta.health.course.session.networkdata.SessionResponse
import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
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
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import fit.asta.health.profile.model.network.NetUserProfile
import fit.asta.health.profile.model.network.NetUserProfileAvailableRes
import fit.asta.health.profile.model.network.NetUserProfileRes
import fit.asta.health.subscription.networkdata.SubscriptionDataResponse
import fit.asta.health.subscription.networkdata.SubscriptionStatusResponse
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes
import fit.asta.health.tools.water.model.network.NetBeverage
import fit.asta.health.tools.water.model.network.NetBeverageRes
import fit.asta.health.tools.water.model.network.NetWaterToolRes
import fit.asta.health.utils.NetworkUtil
import okhttp3.OkHttpClient

class RestApi(baseUrl: String, client: OkHttpClient) :
    RemoteApis {

    private val apiService: ApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(ApiService::class.java)

    //User Profile
    override suspend fun isUserProfileAvailable(userId: String): NetUserProfileAvailableRes {
        return apiService.isUserProfileAvailable(userId)
    }

    override suspend fun updateUserProfile(netUserProfile: NetUserProfile): Status {
        return apiService.updateUserProfile(netUserProfile)
    }

    override suspend fun getUserProfile(userId: String): NetUserProfileRes {
        return apiService.getUserProfile(userId)
    }

    override suspend fun getHealthProperties(propertyType: String): NetHealthPropertiesRes {
        return apiService.getHealthProperties(propertyType)
    }

    //Home page
    override suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): NetHealthToolsRes {
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

    /*
    // Scheduler Endpoints
    override suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): Response<AstaSchedulerPutResponse> {
        return apiService.updateScheduleDataOnBackend(schedule)
    }

    override suspend fun getScheduleDataFromBackend(scheduleId: String): Response<AstaSchedulerGetResponse> {
        return apiService.getScheduleDataFromBackend(scheduleId)
    }

    override suspend fun getScheduleListDataFromBackend(userId: String): Response<AstaSchedulerGetListResponse> {
        return apiService.getScheduleListDataFromBackend(userId)
    }

    override suspend fun deleteScheduleFromBackend(scheduleId: String): Response<AstaSchedulerDeleteResponse> {
        return apiService.deleteScheduleFromBackend(scheduleId)
    }

    // Tags Endpoints
    override suspend fun getTagListDataFromBackend(userId: String): Response<AstaGetTagsListResponse> {
        return apiService.getTagListDataFromBackend(userId)
    }

    override suspend fun updateScheduleTag(schedule: ScheduleTagNetData): Status {
        return apiService.updateScheduleTag(schedule)
    }

    // Media Endpoints
    override suspend fun getAllUserMedia(userId: String): Status {
        return apiService.getAllUserMedia(userId)
    }

    override suspend fun updateUserMedia(schedule: ScheduleTagNetData): Status {
        return apiService.updateUserMedia(schedule)
    }
     */

    //Health Tool - Water Endpoints
    override suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String
    ): NetWaterToolRes {
        return apiService.getWaterTool(userId, latitude, longitude, location, startDate, endDate)
    }

    override suspend fun updateBeverage(beverage: NetBeverage): Status {
        return apiService.updateBeverage(beverage)
    }

    override suspend fun updateBeverageQty(beverage: NetBeverage): Status {
        return apiService.updateBeverageQty(beverage)
    }

    override suspend fun getBeverageList(userId: String): NetBeverageRes {
        return apiService.getBeverageList(userId)
    }

    //Health Tool - Sunlight Endpoints
    override suspend fun getSunlightTool(userId: String): NetSunlightToolRes {
        return apiService.getSunlightTool(userId)
    }

    //Health Tool - Walking Endpoints
    override suspend fun getWalkingTool(userId: String): NetWalkingToolRes {
        return apiService.getWalkingTool(userId)
    }

    //Testimonial Endpoints
    override suspend fun getTestimonials(limit: Int, index: Int): NetTestimonialsRes {
        return apiService.getTestimonials(limit, index)
    }

    override suspend fun updateTestimonial(netTestimonial: NetTestimonial): Status {
        return apiService.updateTestimonial(netTestimonial)
    }

    override suspend fun getUserTestimonial(userId: String): NetTestimonialRes {
        return apiService.getUserTestimonial(userId)
    }

    //Feedback Endpoints
    override suspend fun getFeedbackQuestions(userId: String, featureId: String): NetFeedbackRes {
        return apiService.getFeedbackQuestions(userId, featureId)
    }

    override suspend fun postUserFeedback(feedback: NetUserFeedback): Status {
        return apiService.postUserFeedback(feedback)
    }

    //Old Endpoints -------------------------------------------------------------------------------
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

    override suspend fun putTestimonial(testimonial: TestimonialNetData): Status {
        return apiService.putTestimonial(testimonial)
    }

    override suspend fun getTestimonialList(limit: Int, index: Int): TestimonialListResponse {
        return apiService.getTestimonialList(limit, index)
    }

    override suspend fun getTestimonial(userId: String): TestimonialResponse {
        return apiService.getTestimonial(userId)
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
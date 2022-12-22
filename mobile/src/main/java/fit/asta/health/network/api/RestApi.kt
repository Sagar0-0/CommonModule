package fit.asta.health.network.api

import fit.asta.health.common.multiselect.data.UserInputs
import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
import fit.asta.health.network.data.MultiFileUploadRes
import fit.asta.health.network.data.SingleFileUploadRes
import fit.asta.health.network.data.Status
import fit.asta.health.old_course.details.networkdata.CourseDetailsResponse
import fit.asta.health.old_course.listing.networkdata.CoursesListNetData
import fit.asta.health.old_course.session.networkdata.SessionResponse
import fit.asta.health.old_scheduler.networkdata.ScheduleNetData
import fit.asta.health.old_scheduler.networkdata.ScheduleResponse
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagNetData
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagResponse
import fit.asta.health.old_scheduler.tags.networkdata.ScheduleTagsResponse
import fit.asta.health.old_subscription.networkdata.SubscriptionDataResponse
import fit.asta.health.old_subscription.networkdata.SubscriptionStatusResponse
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import fit.asta.health.profile.model.network.NetUserProfileAvailableRes
import fit.asta.health.profile.model.network.NetUserProfileRes
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.scheduler.model.net.tag.AstaGetTagsListResponse
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes
import fit.asta.health.tools.water.model.network.ModifiedWaterTool
import fit.asta.health.tools.water.model.network.NetBeverage
import fit.asta.health.tools.water.model.network.NetBeverageRes
import fit.asta.health.tools.water.model.network.NetWaterToolRes
import fit.asta.health.utils.NetworkUtil
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response


class RestApi(baseUrl: String, client: OkHttpClient) :
    RemoteApis {

    private val apiService: ApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(ApiService::class.java)

    //User Profile
    override suspend fun isUserProfileAvailable(userId: String): NetUserProfileAvailableRes {
        return apiService.isUserProfileAvailable(userId)
    }

    override suspend fun updateUserProfile(userProfile: UserProfile): Status {
        return apiService.updateUserProfile(userProfile)
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

    override suspend fun updateSelectedTools(toolIds: NetSelectedTools): Status {
        return apiService.updateSelectedTools(toolIds)
    }


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

    override suspend fun deleteScheduleDataFromBackend(scheduleId: String): Response<AstaSchedulerDeleteResponse> {
        return apiService.deleteScheduleDataFromBackend(scheduleId)
    }

    // Tags Endpoints
    override suspend fun getTagListFromBackend(userId: String): Response<AstaGetTagsListResponse> {
        return apiService.getTagListFromBackend(userId)
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

    override suspend fun updateWaterTool(modifiedWaterTool: ModifiedWaterTool): Status {
        return apiService.updateWaterTool(modifiedWaterTool)
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
    override suspend fun getTestimonials(index: Int, limit: Int): NetTestimonialsRes {
        return apiService.getTestimonials(index, limit)
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

    //File upload Endpoints
    override suspend fun uploadFile(
        id: String,
        uid: String,
        feature: String,
        file: MultipartBody.Part
    ): SingleFileUploadRes {
        return apiService.uploadFile(id, uid, feature, file)
    }

    override suspend fun uploadFile(
        id: String, uid: String, feature: String, file: MultipartBody.Part,
        progressCallback: ProgressCallback?
    ): SingleFileUploadRes {
        return apiService.uploadFile(id, uid, feature, file, progressCallback)
    }

    override suspend fun uploadFiles(body: RequestBody?, files: MultipartBody): MultiFileUploadRes {
        return apiService.uploadFiles(body, files)
    }

    override suspend fun uploadFiles(
        body: RequestBody?,
        files: MultipartBody,
        progressCallback: ProgressCallback?
    ): MultiFileUploadRes {
        return apiService.uploadFiles(body, files, progressCallback)
    }

    override suspend fun deleteFile(Id: String, feature: String): Status {
        return apiService.deleteFile(Id, feature)
    }

    override suspend fun deleteFiles(Id: String, feature: String): Status {
        return apiService.deleteFiles(Id, feature)
    }

    //Old Endpoints -------------------------------------------------------------------------------
    override suspend fun getCourseDetails(courseId: String): CourseDetailsResponse {
        return apiService.getCourseDetails(courseId)
    }

    override suspend fun getCoursesList(
        categoryId: String,
        index: Int,
        limit: Int
    ): CoursesListNetData {
        return apiService.getCoursesList(categoryId, index, limit)
    }

    override suspend fun getTodayPlan(userId: String): TodayPlanNetData {
        return apiService.getTodayPlan(userId)
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

    override suspend fun getMultiSelectionData(uid: String): UserInputs {
        return apiService.getMultiSelectionData(uid)
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
package fit.asta.health.network.api

import fit.asta.health.common.multiselect.data.UserInputs
import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
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
import fit.asta.health.tools.water.model.network.NetBeverage
import fit.asta.health.tools.water.model.network.NetBeverageRes
import fit.asta.health.tools.water.model.network.NetWaterToolRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


interface RemoteApis {

    //User Profile
    suspend fun isUserProfileAvailable(userId: String): NetUserProfileAvailableRes
    suspend fun updateUserProfile(userProfile: UserProfile): Status
    suspend fun getUserProfile(userId: String): NetUserProfileRes
    suspend fun getHealthProperties(propertyType: String): NetHealthPropertiesRes

    //Home page
    suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String,
        time: String
    ): NetHealthToolsRes

    suspend fun updateSelectedTools(toolIds: NetSelectedTools): Status

    // Scheduler Endpoints
    suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): Response<AstaSchedulerPutResponse>
    suspend fun getScheduleDataFromBackend(scheduleId: String): Response<AstaSchedulerGetResponse>
    suspend fun getScheduleListDataFromBackend(userId: String): Response<AstaSchedulerGetListResponse>
    suspend fun deleteScheduleDataFromBackend(scheduleId: String): Response<AstaSchedulerDeleteResponse>

    // Tags Endpoints
    suspend fun getTagListFromBackend(userId: String): Response<AstaGetTagsListResponse>
    suspend fun updateScheduleTag(schedule: ScheduleTagNetData): Status

    // Media Endpoints
    suspend fun getAllUserMedia(userId: String): Status
    suspend fun updateUserMedia(schedule: ScheduleTagNetData): Status

    //Health Tool - Water Endpoints
    suspend fun getWaterTool(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String,
        endDate: String
    ): NetWaterToolRes

    suspend fun updateBeverage(beverage: NetBeverage): Status
    suspend fun updateBeverageQty(beverage: NetBeverage): Status
    suspend fun getBeverageList(userId: String): NetBeverageRes

    //Health Tool - Sunlight Endpoints
    suspend fun getSunlightTool(userId: String): NetSunlightToolRes

    //Health Tool - Walking Endpoints
    suspend fun getWalkingTool(userId: String): NetWalkingToolRes

    //Testimonial Endpoints
    suspend fun getTestimonials(index: Int, limit: Int): NetTestimonialsRes
    suspend fun updateTestimonial(netTestimonial: NetTestimonial): Status
    suspend fun getUserTestimonial(userId: String): NetTestimonialRes

    //Feedback Endpoints
    suspend fun getFeedbackQuestions(userId: String, featureId: String): NetFeedbackRes
    suspend fun postUserFeedback(feedback: NetUserFeedback): Status

    //File upload Endpoint
    suspend fun uploadFiles(description: RequestBody?, file: MultipartBody): Status

    //Old Endpoints -------------------------------------------------------------------------------
    suspend fun getCoursesList(categoryId: String, index: Int, limit: Int): CoursesListNetData
    suspend fun getCourseDetails(courseId: String): CourseDetailsResponse
    suspend fun getSubscriptionPlans(): SubscriptionDataResponse
    suspend fun getSubscriptionStatus(userId: String): SubscriptionStatusResponse
    suspend fun getSession(userId: String, courseId: String, sessionId: String): SessionResponse
    suspend fun getMultiSelectionData(uid: String): UserInputs

    suspend fun getScheduledPlan(userId: String, scheduleId: String): ScheduleResponse
    suspend fun postScheduledPlan(scheduleData: ScheduleNetData)
    suspend fun putRescheduledPlan(scheduleData: ScheduleNetData)
    suspend fun postScheduleTag(schedule: ScheduleTagNetData): Status
    suspend fun getScheduleTag(userId: String, tagId: String): ScheduleTagResponse
    suspend fun putScheduleTag(schedule: ScheduleTagNetData): Status
    suspend fun getScheduleTagList(): ScheduleTagsResponse
    suspend fun getTodayPlan(userId: String): TodayPlanNetData
}
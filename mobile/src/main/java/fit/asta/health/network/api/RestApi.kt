package fit.asta.health.network.api

import fit.asta.health.common.multiselect.data.UserInputs
import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import fit.asta.health.navigation.today.networkdata.TodayPlanNetData
import fit.asta.health.network.data.MultiFileUploadRes
import fit.asta.health.network.data.SingleFileUploadRes
import fit.asta.health.network.data.Status
import fit.asta.health.network.data.UploadInfo
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
import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes
import fit.asta.health.utils.NetworkUtil
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody


class RestApi(baseUrl: String, client: OkHttpClient) :
    RemoteApis {

    private val apiService: ApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(ApiService::class.java)

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

    //Health Tool - Sunlight Endpoints
    override suspend fun getSunlightTool(userId: String): NetSunlightToolRes {
        return apiService.getSunlightTool(userId)
    }

    //Health Tool - Walking Endpoints
    override suspend fun getWalkingTool(userId: String): NetWalkingToolRes {
        return apiService.getWalkingTool(userId)
    }

    //File upload Endpoints
    override suspend fun uploadFile(
        info: UploadInfo,
        file: MultipartBody.Part
    ): SingleFileUploadRes {
        return apiService.uploadFile(info, file)
    }

    override suspend fun uploadFile(
        info: UploadInfo,
        file: MultipartBody.Part,
        progressCallback: ProgressCallback?
    ): SingleFileUploadRes {
        return apiService.uploadFile(info, file, progressCallback)
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
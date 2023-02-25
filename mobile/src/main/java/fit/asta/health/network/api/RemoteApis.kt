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
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface RemoteApis {

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

    //File upload Endpoints
    suspend fun uploadFile(info: UploadInfo, file: MultipartBody.Part): SingleFileUploadRes

    suspend fun uploadFile(
        info: UploadInfo,
        file: MultipartBody.Part,
        progressCallback: ProgressCallback?
    ): SingleFileUploadRes

    suspend fun uploadFiles(body: RequestBody?, files: MultipartBody): MultiFileUploadRes
    suspend fun uploadFiles(
        body: RequestBody?,
        files: MultipartBody,
        progressCallback: ProgressCallback?
    ): MultiFileUploadRes

    suspend fun deleteFile(Id: String, feature: String): Status
    suspend fun deleteFiles(Id: String, feature: String): Status

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
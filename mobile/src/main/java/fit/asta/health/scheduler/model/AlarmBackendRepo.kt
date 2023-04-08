package fit.asta.health.scheduler.model

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.Status
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.doman.model.SchedulerGetData
import fit.asta.health.scheduler.model.doman.model.SchedulerGetListData
import fit.asta.health.scheduler.model.doman.model.SchedulerGetTagsList
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.scheduler.model.net.tag.ScheduleTagNetData
import kotlinx.coroutines.flow.Flow

interface AlarmBackendRepo {

    //Scheduler
    suspend fun updateScheduleDataOnBackend(schedule: AlarmEntity): NetworkResult<AstaSchedulerPutResponse>
    suspend fun getScheduleDataFromBackend(scheduleId: String): Flow<NetworkResult<SchedulerGetData>>
    suspend fun getScheduleListDataFromBackend(userId: String):  Flow<NetworkResult<SchedulerGetListData>>
    suspend fun deleteScheduleDataFromBackend(scheduleId: String): NetworkResult<AstaSchedulerDeleteResponse>

    //Tags
    suspend fun getTagListFromBackend(userId: String):Flow< NetworkResult<SchedulerGetTagsList>>
    suspend fun updateScheduleTag(schedule: ScheduleTagNetData): NetworkResult<Status>

    //Media
    suspend fun getAllUserMedia(userId: String): Flow<NetworkResult<Status>>
    suspend fun updateUserMedia(schedule: ScheduleTagNetData): NetworkResult<Status>
}
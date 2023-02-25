package fit.asta.health.old.scheduler

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.old.scheduler.data.ScheduleData
import fit.asta.health.old.scheduler.data.ScheduleDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ScheduleRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: ScheduleDataMapper
) : fit.asta.health.old.scheduler.ScheduleRepo {

    override suspend fun fetchScheduledData(
        userId: String,
        scheduleId: String
    ): Flow<ScheduleData> {
        return flow {
            emit(dataMapper.toScheduleMap(remoteApi.getScheduledPlan(userId, scheduleId)))
        }
    }

    override suspend fun schedulePlan(userId: String, schedule: ScheduleData): Flow<String> {
        return flow {
            schedule.let {
                val data = dataMapper.toRescheduleMap(userId, schedule)
                remoteApi.postScheduledPlan(data)
            }
        }
    }

    override suspend fun reschedulePlan(userId: String, schedule: ScheduleData): Flow<String> {
        return flow {
            schedule.let {
                val data = dataMapper.toRescheduleMap(userId, schedule)
                remoteApi.putRescheduledPlan(data)
            }
        }
    }
}

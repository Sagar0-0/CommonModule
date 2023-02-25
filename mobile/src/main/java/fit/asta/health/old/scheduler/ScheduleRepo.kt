package fit.asta.health.old.scheduler

import fit.asta.health.old.scheduler.data.ScheduleData
import kotlinx.coroutines.flow.Flow

interface ScheduleRepo {
    suspend fun schedulePlan(userId: String, schedule: ScheduleData): Flow<String>
    suspend fun fetchScheduledData(userId: String, scheduleId: String): Flow<ScheduleData>
    suspend fun reschedulePlan(userId: String, schedule: ScheduleData): Flow<String>
}
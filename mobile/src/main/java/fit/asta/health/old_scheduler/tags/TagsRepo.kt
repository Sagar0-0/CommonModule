package fit.asta.health.old_scheduler.tags

import fit.asta.health.network.data.Status
import fit.asta.health.old_scheduler.tags.data.ScheduleTagData
import kotlinx.coroutines.flow.Flow

interface TagsRepo {
    suspend fun createTag(tag: ScheduleTagData?): Flow<Status>
    suspend fun fetchTag(userId: String, tagId: String): Flow<ScheduleTagData>
    suspend fun updateTag(tag: ScheduleTagData?): Flow<Status>
    suspend fun fetchTagList(): Flow<List<ScheduleTagData>>
}
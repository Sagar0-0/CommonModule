package fit.asta.health.schedule.tags

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.Status
import fit.asta.health.schedule.tags.data.ScheduleTagData
import fit.asta.health.schedule.tags.data.TagDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TagsRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: TagDataMapper

) : TagsRepo {

    override suspend fun createTag(tag: ScheduleTagData?): Flow<Status> {
        return flow {
            tag?.let {
                val data = dataMapper.toNetTag(tag)
                remoteApi.postScheduleTag(data)
            }
        }
    }

    override suspend fun fetchTag(userId: String, tagId: String): Flow<ScheduleTagData> {
        return flow {
            emit(dataMapper.toTag(remoteApi.getScheduleTag(userId, tagId).data))
        }
    }

    override suspend fun updateTag(tag: ScheduleTagData?): Flow<Status> {
        return flow {
            tag?.let {
                val data = dataMapper.toNetTag(tag)
                remoteApi.putScheduleTag(data)
            }
        }
    }

    override suspend fun fetchTagList(): Flow<List<ScheduleTagData>> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getScheduleTagList()))
        }
    }
}
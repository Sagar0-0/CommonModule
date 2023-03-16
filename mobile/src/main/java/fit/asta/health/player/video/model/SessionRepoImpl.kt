package fit.asta.health.player.video.model

import fit.asta.health.player.video.model.api.VideoApi
import fit.asta.health.player.video.model.data.SessionData
import fit.asta.health.player.video.model.data.SessionDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SessionRepoImpl(
    private val remoteApi: VideoApi,
    private val dataMapper: SessionDataMapper
) : SessionRepo {
    override suspend fun fetchSession(
        userId: String,
        courseId: String,
        sessionId: String
    ): Flow<SessionData> {
        return flow { emit(dataMapper.toMap(remoteApi.getSession(userId, courseId, sessionId))) }
    }
}
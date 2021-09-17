package fit.asta.health.course.session

import fit.asta.health.course.session.data.SessionData
import fit.asta.health.course.session.data.SessionDataMapper
import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SessionRepoImpl(
    private val remoteApi: RemoteApis,
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
package fit.asta.health.old_course.session

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.old_course.session.data.SessionData
import fit.asta.health.old_course.session.data.SessionDataMapper
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
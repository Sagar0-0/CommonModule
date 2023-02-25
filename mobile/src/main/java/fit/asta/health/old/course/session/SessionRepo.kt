package fit.asta.health.old.course.session

import fit.asta.health.old.course.session.data.SessionData
import kotlinx.coroutines.flow.Flow

interface SessionRepo {
    suspend fun fetchSession(userId: String, courseId: String, sessionId: String): Flow<SessionData>
}
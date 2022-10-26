package fit.asta.health.old_course.session

import fit.asta.health.old_course.session.data.SessionData
import kotlinx.coroutines.flow.Flow

interface SessionRepo {
    suspend fun fetchSession(userId: String, courseId: String, sessionId: String): Flow<SessionData>
}
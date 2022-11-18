package fit.asta.health.player.audio

import kotlinx.coroutines.flow.Flow

interface SessionRepo {
    suspend fun fetchSession(userId: String, courseId: String, sessionId: String): Flow<SessionData>
}
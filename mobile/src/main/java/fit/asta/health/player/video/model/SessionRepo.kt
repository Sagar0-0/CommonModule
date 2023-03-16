package fit.asta.health.player.video.model

import fit.asta.health.player.video.model.data.SessionData
import kotlinx.coroutines.flow.Flow

interface SessionRepo {
    suspend fun fetchSession(userId: String, courseId: String, sessionId: String): Flow<SessionData>
}
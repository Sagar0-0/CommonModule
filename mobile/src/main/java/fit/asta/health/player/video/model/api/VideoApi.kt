package fit.asta.health.player.video.model.api

import fit.asta.health.player.video.model.networkdata.SessionResponse

//Video Endpoints
interface VideoApi {
    suspend fun getSession(userId: String, courseId: String, sessionId: String): SessionResponse
}
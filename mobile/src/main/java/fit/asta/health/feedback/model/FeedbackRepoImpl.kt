package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.domain.Feedback
import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FeedbackRepoImpl(
    private val remoteApi: RemoteApis,
    private val mapper: FeedbackDataMapper,
) : FeedbackRepo {

    override suspend fun getFeedback(userId: String): Flow<Feedback> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getFeedback(userId = userId)
                )
            )
        }
    }
}
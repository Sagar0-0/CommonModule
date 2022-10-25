package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.domain.Feedback
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FeedbackRepoImpl(
    private val remoteApi: RemoteApis,
    private val mapper: FeedbackDataMapper,
) : FeedbackRepo {

    override suspend fun getFeedback(userId: String, featureId: String): Flow<Feedback> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getFeedbackQuestions(userId = userId, featureId = featureId)
                )
            )
        }
    }

    override suspend fun postUserFeedback(feedback: NetUserFeedback): Flow<Status> {
        return flow {
            emit(
                remoteApi.postUserFeedback(feedback)
            )
        }
    }
}
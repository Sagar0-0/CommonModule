package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.domain.Feedback
import fit.asta.health.feedback.model.network.FeedbackQuesResponse
import fit.asta.health.feedback.model.network.UserFeedback

class FeedbackDataMapper {

    fun mapToDomainModel(networkModel: FeedbackQuesResponse): Feedback {
        return Feedback(
        )
    }

    fun mapToNetworkModel(domainModel: Feedback): UserFeedback {
        TODO("Not yet implemented")
    }
}
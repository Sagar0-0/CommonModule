package fit.asta.health.feedback.data.utils

import fit.asta.health.feedback.data.domain.Feedback
import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.UserFeedbackDTO

class FeedbackDataMapper {

    fun mapToDomainModel(networkModel: FeedbackQuesDTO): Feedback {
        return Feedback(
        )
    }

    fun mapToNetworkModel(domainModel: Feedback): UserFeedbackDTO {
        TODO("Not yet implemented")
    }
}
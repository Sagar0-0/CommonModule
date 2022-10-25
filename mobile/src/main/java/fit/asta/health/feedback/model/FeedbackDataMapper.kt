package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.domain.Feedback
import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback

class FeedbackDataMapper {

    fun mapToDomainModel(networkModel: NetFeedbackRes): Feedback {
        return Feedback(
        )
    }

    fun mapToNetworkModel(domainModel: Feedback): NetUserFeedback {
        TODO("Not yet implemented")
    }
}
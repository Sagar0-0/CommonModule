package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.domain.Feedback
import fit.asta.health.feedback.model.network.response.NetFeedbackRes

class FeedbackDataMapper {

    fun mapToDomainModel(networkModel: NetFeedbackRes): Feedback {
        return Feedback(
        )
    }

    fun mapToNetworkModel(domainModel: Feedback): NetFeedbackRes {
        TODO("Not yet implemented")
    }
}
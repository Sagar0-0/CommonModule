package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.domain.Feedback
import fit.asta.health.feedback.model.network.response.NetFeedbackRes
import fit.asta.health.utils.DomainMapper

class FeedbackDataMapper : DomainMapper<NetFeedbackRes, Feedback> {

    override fun mapToDomainModel(networkModel: NetFeedbackRes): Feedback {
        return Feedback(
        )
    }

    override fun mapFromDomainModel(domainModel: Feedback): NetFeedbackRes {
        TODO("Not yet implemented")
    }
}
package fit.asta.health.payments.sub.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.sub.model.SubscriptionResponse

interface SubscriptionRepo {
    suspend fun getData(
        uid: String,
        country: String,
        date: String
    ): ResponseState<SubscriptionResponse>
}

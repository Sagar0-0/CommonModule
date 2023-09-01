package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.subscription.remote.model.SubscriptionResponse

interface SubscriptionRepo {
    suspend fun getData(
        uid: String,
        country: String
    ): ResponseState<SubscriptionResponse>
}

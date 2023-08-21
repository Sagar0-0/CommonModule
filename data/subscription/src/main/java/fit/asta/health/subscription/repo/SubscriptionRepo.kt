package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.subscription.model.SubscriptionResponse

interface SubscriptionRepo {
    suspend fun getData(
        uid: String,
        country: String,
        date: String
    ): fit.asta.health.common.utils.ResponseState<SubscriptionResponse>
}

package fit.asta.health.subscription.repo

import com.example.common.utils.ResponseState
import fit.asta.health.subscription.model.SubscriptionResponse

interface SubscriptionRepo {
    suspend fun getData(
        uid: String,
        country: String,
        date: String
    ): ResponseState<SubscriptionResponse>
}

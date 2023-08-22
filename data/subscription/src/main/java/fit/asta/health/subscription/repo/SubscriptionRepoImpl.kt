package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.subscription.model.SubscriptionResponse
import fit.asta.health.subscription.remote.SubscriptionApi
import javax.inject.Inject

class SubscriptionRepoImpl
@Inject constructor(
    private val remoteApi: SubscriptionApi
) : SubscriptionRepo {

    override suspend fun getData(
        uid: String,
        country: String,
        date: String
    ): ResponseState<SubscriptionResponse> {
        return getResponseState { remoteApi.getData(uid, country, date) }
    }
}
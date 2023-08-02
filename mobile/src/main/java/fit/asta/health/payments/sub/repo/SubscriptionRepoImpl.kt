package fit.asta.health.payments.sub.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.sub.api.SubscriptionApi
import fit.asta.health.payments.sub.model.SubscriptionResponse
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
        return try {
            val response = remoteApi.getData(uid, country, date)
            ResponseState.Success(response)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }


}
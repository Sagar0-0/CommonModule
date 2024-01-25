package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.subscription.remote.SubscriptionApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubscriptionRepoImpl
@Inject constructor(
    private val remoteApi: SubscriptionApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SubscriptionRepo {

    override suspend fun getFinalAmountData(
        type: String,
        categoryId: String,
        productId: String,
        country: String
    ) = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getFinalAmountData(type, categoryId, productId, country) }
    }

    override suspend fun getSubscriptionData(country: String, uid: String) =
        withContext(coroutineDispatcher) {
            getApiResponseState { remoteApi.getSubscriptionData(country, uid) }
        }

    override suspend fun getUserSubscribedPlan(uid: String) = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getUserSubscribedPlan(uid) }
    }

    override suspend fun getSubscriptionDurationsData(
        categoryId: String,
        country: String
    ) = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getSubscriptionDurationsData(country, categoryId) }
    }
}

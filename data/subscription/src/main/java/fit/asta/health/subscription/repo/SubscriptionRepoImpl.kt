package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.subscription.remote.SubscriptionApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class
SubscriptionRepoImpl
@Inject constructor(
    private val remoteApi: SubscriptionApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SubscriptionRepo {
    override suspend fun getData(
        uid: String,
        country: String
    ) = withContext(coroutineDispatcher) {
        getResponseState { remoteApi.getData(uid, country) }
    }
}
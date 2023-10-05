package fit.asta.health.discounts.repo

import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.discounts.remote.DiscountsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DiscountsRepoImpl(
    private val remoteApi: DiscountsApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : DiscountsRepo {

    override suspend fun getData() = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getData() }
    }
}


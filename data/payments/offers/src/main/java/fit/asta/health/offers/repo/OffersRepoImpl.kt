package fit.asta.health.offers.repo

import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.offers.remote.OffersApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OffersRepoImpl(
    private val remoteApi: OffersApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : OffersRepo {

    override suspend fun getData() = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getData() }
    }
}


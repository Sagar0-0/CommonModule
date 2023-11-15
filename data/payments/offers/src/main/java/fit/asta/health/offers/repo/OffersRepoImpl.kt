package fit.asta.health.offers.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.offers.remote.OffersApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OffersRepoImpl @Inject constructor(
    private val remoteApi: OffersApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : OffersRepo {

    override suspend fun getData() = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getData() }
    }
}


package fit.asta.health.wallet.repo

import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.wallet.remote.WalletApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WalletRepoImpl
@Inject constructor(
    private val remoteApi: WalletApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WalletRepo {

    override suspend fun getData(uid: String) = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getData(uid) }
    }


}
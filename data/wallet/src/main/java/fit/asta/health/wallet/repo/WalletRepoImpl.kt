package fit.asta.health.wallet.repo

import fit.asta.health.wallet.api.WalletApi
import fit.asta.health.common.utils.getResponseState
import javax.inject.Inject

class WalletRepoImpl
@Inject constructor(
    private val remoteApi: WalletApi
) : WalletRepo {

    override suspend fun getData(uid: String) = getResponseState { remoteApi.getData(uid) }


}
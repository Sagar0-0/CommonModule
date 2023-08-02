package fit.asta.health.payments.wallet.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.wallet.api.WalletApi
import fit.asta.health.payments.wallet.model.WalletResponse
import javax.inject.Inject

class WalletRepoImpl
@Inject constructor(
    private val remoteApi: WalletApi
) : WalletRepo {

    override suspend fun getData(uid: String): ResponseState<WalletResponse> {
        return try {
            val response = remoteApi.getData(uid)
            ResponseState.Success(response)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }


}
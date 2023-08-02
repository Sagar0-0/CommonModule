package fit.asta.health.payments.wallet.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.wallet.model.WalletResponse


class FakeWalletRepoImpl : WalletRepo {
    private var isError = false
    fun setError(value: Boolean) {
        isError = value
    }

    override suspend fun getData(uid: String): ResponseState<WalletResponse> {
        return if (isError) {
            ResponseState.Error(Exception("Error"))
        } else {
            ResponseState.Success(WalletResponse())
        }
    }
}
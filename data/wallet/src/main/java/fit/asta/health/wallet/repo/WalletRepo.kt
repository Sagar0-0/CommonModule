package fit.asta.health.wallet.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.wallet.remote.model.WalletResponse

interface WalletRepo {
    suspend fun getData(uid: String): ResponseState<WalletResponse>
}

package fit.asta.health.wallet.repo

import fit.asta.health.wallet.model.WalletResponse
import fit.asta.health.common.utils.ResponseState

interface WalletRepo {
    suspend fun getData(uid: String): ResponseState<WalletResponse>
}

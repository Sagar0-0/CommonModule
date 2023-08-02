package fit.asta.health.payments.wallet.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.wallet.model.WalletResponse

interface WalletRepo {
    suspend fun getData(uid: String): ResponseState<WalletResponse>
}

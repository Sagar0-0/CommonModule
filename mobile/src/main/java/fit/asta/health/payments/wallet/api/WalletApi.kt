package fit.asta.health.payments.wallet.api

import fit.asta.health.payments.wallet.model.WalletResponse

interface WalletApi {

    suspend fun getData(uid: String): WalletResponse
}
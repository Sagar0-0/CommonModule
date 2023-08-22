package com.example.wallet.repo

import com.example.wallet.model.WalletResponse
import fit.asta.health.common.utils.ResponseState

interface WalletRepo {
    suspend fun getData(uid: String): ResponseState<WalletResponse>
}

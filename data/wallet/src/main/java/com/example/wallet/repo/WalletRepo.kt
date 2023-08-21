package com.example.wallet.repo

import com.example.common.utils.ResponseState
import com.example.wallet.model.WalletResponse

interface WalletRepo {
    suspend fun getData(uid: String): ResponseState<WalletResponse>
}

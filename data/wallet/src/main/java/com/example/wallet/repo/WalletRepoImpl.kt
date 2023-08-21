package com.example.wallet.repo

import com.example.common.utils.getResponseState
import com.example.wallet.api.WalletApi
import javax.inject.Inject

class WalletRepoImpl
@Inject constructor(
    private val remoteApi: WalletApi
) : WalletRepo {

    override suspend fun getData(uid: String) = getResponseState { remoteApi.getData(uid) }


}
package com.example.wallet.api

import com.example.wallet.model.WalletResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WalletApi {

    @GET("payment/wallet/get/?")
    suspend fun getData(@Query("uid") uid: String): WalletResponse

}
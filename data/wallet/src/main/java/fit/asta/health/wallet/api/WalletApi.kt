package fit.asta.health.wallet.api

import fit.asta.health.wallet.model.WalletResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WalletApi {

    @GET("payment/wallet/get/?")
    suspend fun getData(@Query("uid") uid: String): WalletResponse

}
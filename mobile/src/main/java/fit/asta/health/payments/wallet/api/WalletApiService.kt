package fit.asta.health.payments.wallet.api

import fit.asta.health.payments.wallet.model.WalletResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WalletApiService {

    @GET("payment/wallet/get/?")
    suspend fun getData(@Query("uid") uid: String): WalletResponse

}
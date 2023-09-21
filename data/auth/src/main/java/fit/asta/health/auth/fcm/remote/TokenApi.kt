package fit.asta.health.auth.fcm.remote

import fit.asta.health.common.utils.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface TokenApi {

    @PUT("/userProfile/device/token/")
    suspend fun sendToken(@Body tokenDTO: TokenDTO): Response<TokenResponse>
}
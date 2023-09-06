package fit.asta.health.auth.remote

import retrofit2.http.DELETE
import retrofit2.http.Query

interface AuthApi {

    @DELETE("userProfile/delete/")
    suspend fun deleteAccount(@Query("uid") uid: String) : DeleteAccountResponse
}
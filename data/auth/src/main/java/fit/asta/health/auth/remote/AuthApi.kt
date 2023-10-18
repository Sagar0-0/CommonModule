package fit.asta.health.auth.remote

import fit.asta.health.common.utils.Response
import retrofit2.http.DELETE
import retrofit2.http.Query

interface AuthApi {

    @DELETE("userProfile/delete/")
    suspend fun deleteAccount(@Query("uid") uid: String): Response<DeleteAccountResponse>
}
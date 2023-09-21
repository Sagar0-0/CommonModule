package fit.asta.health.auth.repo

import com.google.firebase.auth.AuthCredential
import fit.asta.health.auth.fcm.remote.TokenDTO
import fit.asta.health.auth.fcm.remote.TokenResponse
import fit.asta.health.auth.model.domain.User
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    val userData: Flow<UserPreferencesData>
    suspend fun setLoginDone()
    suspend fun setBasicProfileDone()
    suspend fun setLogoutDone()
    fun isAuthenticated(): Boolean
    fun getUserId(): String?
    fun getUser(): User?
    fun signInWithCredential(authCredential: AuthCredential): Flow<ResponseState<User>>
    fun linkWithCredential(authCredential: AuthCredential): Flow<ResponseState<User>>
    fun signOut(): ResponseState<Boolean>
    suspend fun deleteAccount(): Flow<ResponseState<Boolean>>
    suspend fun setIsFcmTokenUploaded(value: Boolean)
    suspend fun resetReferralCode()
    suspend fun uploadFcmToken(tokenDTO: TokenDTO): ResponseState<TokenResponse>
}
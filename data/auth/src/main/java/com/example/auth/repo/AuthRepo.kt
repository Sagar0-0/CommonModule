package fit.asta.health.auth.data.repo

import android.app.Activity
import com.example.auth.model.domain.User
import com.google.firebase.auth.AuthCredential
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun isAuthenticated(): Boolean
    fun getUserId(): String?
    fun getUser(): User?
    fun signInWithCredential(googleAuthCredential: AuthCredential): Flow<ResponseState<Boolean>>
    fun signInWithPhone(phone: String, activity: Activity): ResponseState<String>
    fun verifyPhoneOtp(otp: String): Flow<ResponseState<String>>
    fun signOut(): ResponseState<Boolean>
    fun deleteAccount(): Flow<ResponseState<Boolean>>
}
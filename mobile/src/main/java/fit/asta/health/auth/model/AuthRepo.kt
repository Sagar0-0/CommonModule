package fit.asta.health.auth.model

import android.app.Activity
import com.google.firebase.auth.AuthCredential
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.model.domain.UserCred
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun isAuthenticated(): Boolean
    fun getUserId(): String?
    fun getUser(): User?
    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): Flow<ResponseState<User>>
    fun createUserWithPhone(phone: String, activity: Activity): Flow<ResponseState<String>>
    fun signWithCredential(otp: String): Flow<ResponseState<String>>
    fun createUser(auth: UserCred): Flow<ResponseState<User>>
    fun loginUser(auth: UserCred): Flow<ResponseState<User>>
    fun logout()
}
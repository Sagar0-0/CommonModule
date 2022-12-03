package fit.asta.health.firebase.model

import android.app.Activity
import com.google.firebase.auth.AuthCredential
import fit.asta.health.firebase.model.domain.User
import fit.asta.health.firebase.model.domain.UserCred
import fit.asta.health.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun isAuthenticated(): Boolean
    fun getUserId(): String?
    fun getUser(): User?
    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): Flow<ResultState<User>>
    fun createUserWithPhone(phone: String, activity: Activity): Flow<ResultState<String>>
    fun signWithCredential(otp: String): Flow<ResultState<String>>
    fun createUser(auth: UserCred): Flow<ResultState<User>>
    fun loginUser(auth: UserCred): Flow<ResultState<User>>
    fun logout()
}
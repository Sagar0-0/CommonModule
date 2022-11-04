package fit.asta.health.firebase.model

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import fit.asta.health.firebase.model.domain.User
import fit.asta.health.firebase.model.domain.UserCred
import fit.asta.health.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun isAuthenticated(): Boolean
    fun getUser(): User?
    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): MutableLiveData<User>?
    fun createUserWithPhone(phone: String, activity: Activity): Flow<ResultState<String>>
    fun signWithCredential(otp: String): Flow<ResultState<String>>
    fun createUser(auth: UserCred): Flow<ResultState<String>>
    fun loginUser(auth: UserCred): Flow<ResultState<String>>
}
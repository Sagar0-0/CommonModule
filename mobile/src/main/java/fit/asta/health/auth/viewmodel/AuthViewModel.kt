package fit.asta.health.auth.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.AuthActivity
import fit.asta.health.auth.model.AuthRepo
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.model.domain.UserCred
import fit.asta.health.common.utils.ResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    var state = mutableStateOf(AuthState.Loading)
        private set

    private val _loginState = MutableStateFlow<ResultState<User>?>(null)
    val loginState: StateFlow<ResultState<User>?> = _loginState

    private val _signupState = MutableStateFlow<ResultState<User>?>(null)
    val signupState: StateFlow<ResultState<User>?> = _signupState

    val currentUser: User?
        get() = authRepo.getUser()

    init {
        if (authRepo.getUser() != null) {
            _loginState.value = ResultState.Success(authRepo.getUser()!!)
        }
    }

    fun getUserId() = authRepo.getUserId()

    fun getUser() = authRepo.getUser()

    fun isAuthenticated(): Boolean {

        return authRepo.isAuthenticated()
    }

    fun signupUser(name: String, email: String, password: String) = viewModelScope.launch {
        _signupState.value = ResultState.Loading
        authRepo.createUser(UserCred(email, password)).collect {
            _signupState.value = it
        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _loginState.value = ResultState.Loading
        val result = authRepo.loginUser(UserCred(email, password)).collect {
            _loginState.value = it
        }
    }

    fun logout(context: Context, showSnackBar: (message: String) -> Unit) {
        AuthUI.getInstance().signOut(context)
            .addOnCompleteListener { signOutTask ->

                if (signOutTask.isSuccessful) {
                    AuthActivity.launch(context)
                } else {
                    showSnackBar(signOutTask.exception?.message!!)
                }
            }
        _loginState.value = null
        _signupState.value = null
    }

    fun deleteAccount(context: Context, showSnackBar: (message: String) -> Unit) {

        val currentUser = Firebase.auth.currentUser ?: return
        val credential: AuthCredential = when (currentUser.providerData[1].providerId) {
            "google.com" -> {

                val fireBaseContext = FirebaseAuth.getInstance().app.applicationContext
                val googleAccount = GoogleSignIn.getLastSignedInAccount(fireBaseContext)
                GoogleAuthProvider.getCredential(googleAccount?.idToken, null)

            }

            "phone" -> {

                // How to get the below params(verificationId, code), when we use firebase auth ui?
                PhoneAuthProvider.getCredential(currentUser.phoneNumber!!, "")
            }

            else -> return
        }

        deleteAccount(credential, currentUser, context, showSnackBar)
    }

    private fun deleteAccount(
        credential: AuthCredential,
        user: FirebaseUser,
        context: Context,
        showSnackBar: (message: String) -> Unit
    ) =
        reAuthenticateUser(credential)
            .addOnCompleteListener { reAuthTask ->

                if (reAuthTask.isSuccessful) {

                    user.delete()
                        .addOnCompleteListener { deleteTask ->

                            if (deleteTask.isSuccessful) {
                                AuthActivity.launch(context)
                            } else {
                                showSnackBar(deleteTask.exception?.message!!)
                                Log.d("DeleteAccount", deleteTask.exception?.message!!)
                            }
                        }
                } else { //Handle the exception

                    showSnackBar(reAuthTask.exception?.message!!)
                    Log.d("ReAuth", reAuthTask.exception?.message!!)
                }
            }

    private fun reAuthenticateUser(credential: AuthCredential) =
        Firebase.auth.currentUser!!.reauthenticate(credential)
}
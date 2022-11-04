package fit.asta.health.firebase.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.firebase.model.domain.User
import fit.asta.health.firebase.model.domain.UserCred
import fit.asta.health.utils.ResultState
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

    fun logout() {
        authRepo.logout()
        _loginState.value = null
        _signupState.value = null
    }
}
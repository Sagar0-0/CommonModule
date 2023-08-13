package fit.asta.health.auth.ui.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.data.repo.AuthRepo
import fit.asta.health.auth.data.model.domain.User
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringResId
import fit.asta.health.common.utils.toUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {


    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _deleteState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val deleteState = _deleteState.asStateFlow()

    val currentUser: User?
        get() = authRepo.getUser()

    fun getUser() = authRepo.getUser()

    fun isAuthenticated(): Boolean {
        return authRepo.isAuthenticated()
    }


    fun signInWithGoogleCredentials(authCredential: AuthCredential) {
        _loginState.value = UiState.Loading
        viewModelScope.launch{
            try{
                authRepo.signInWithCredential(authCredential).collect{
                    _loginState.value = it.toUiState()
                }
            }catch (e:Exception){
                _loginState.value = UiState.Error(e.toStringResId())
            }
        }
    }

    fun logout(context: Context, onSuccess: () -> Unit, onFailure: (message: String) -> Unit) {
        AuthUI.getInstance().signOut(context)
            .addOnCompleteListener { signOutTask ->
                if (signOutTask.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(signOutTask.exception?.message!!)
                }
            }
    }

    fun deleteAccount(){
        _deleteState.value = UiState.Loading
        viewModelScope.launch{
            try{
                authRepo.deleteAccount().collect{
                    _deleteState.value = it.toUiState()
                }
            }catch (e:Exception){
                _deleteState.value = UiState.Error(e.toStringResId())
            }
        }
    }

}
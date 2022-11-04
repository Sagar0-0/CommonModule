package fit.asta.health.firebase.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    var state = mutableStateOf(AuthState.Loading)
        private set

    fun getUser() = authRepo.getUser()

    fun isAuthenticated(): Boolean {

        return authRepo.isAuthenticated()
    }
}
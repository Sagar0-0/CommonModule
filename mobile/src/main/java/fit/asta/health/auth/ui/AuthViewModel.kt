package fit.asta.health.auth.ui

import androidx.lifecycle.ViewModel
import fit.asta.health.auth.data.UserRepository


class AuthViewModel : ViewModel() {

    private val repository: UserRepository =
        UserRepository()

    // val user: LiveData<User>
    fun getUser() = repository.user()

    fun isAuthenticated(): Boolean {
        return repository.isAuthenticated()
    }
}
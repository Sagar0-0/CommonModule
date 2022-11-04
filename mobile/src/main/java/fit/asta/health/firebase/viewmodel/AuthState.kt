package fit.asta.health.firebase.viewmodel

import fit.asta.health.firebase.model.domain.User


sealed class AuthState {
    object Loading : AuthState()
    class Success(val user: User) : AuthState()
    class Error(val error: Throwable) : AuthState()
}
package fit.asta.health.auth.intent

import fit.asta.health.auth.model.domain.User


sealed class AuthState {
    object Loading : AuthState()
    class Success(val user: User) : AuthState()
    class Error(val error: Throwable) : AuthState()
}
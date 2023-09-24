package fit.asta.health.feature.auth.screens

import com.google.firebase.auth.AuthCredential
import fit.asta.health.auth.model.domain.User

sealed interface AuthEvent {
    data object ResetLoginState : AuthEvent
    data class NavigateToWebView(val url: String) : AuthEvent
    data class CheckProfileAndNavigate(val user: User) : AuthEvent
    data class SignInWithCredentials(val authCredential: AuthCredential) : AuthEvent


}

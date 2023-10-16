package fit.asta.health.feature.auth.screens

import com.google.firebase.auth.AuthCredential
import fit.asta.health.auth.model.domain.User

sealed interface AuthUiEvent {
    data object ResetLoginState : AuthUiEvent
    data object GetOnboardingData : AuthUiEvent
    data class NavigateToWebView(val url: String) : AuthUiEvent
    data class CheckProfileAndNavigate(val user: User) : AuthUiEvent
    data class SignInWithCredentials(val authCredential: AuthCredential) : AuthUiEvent


}

package fit.asta.health.feature.auth.screens

import com.google.firebase.auth.AuthCredential

sealed interface AuthUiEvent {
    data object OnLoginFailed : AuthUiEvent
    data object GetOnboardingData : AuthUiEvent
    data class NavigateToWebView(val url: String) : AuthUiEvent
    data class SignInWithCredentials(val authCredential: AuthCredential) : AuthUiEvent

}

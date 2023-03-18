package fit.asta.health.firebase.viewmodel

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.LOGIN_SCREEN
import fit.asta.health.SETTINGS_SCREEN
import fit.asta.health.firebase.model.domain.UserCred
import fit.asta.health.firebase.model.service.AuthService
import fit.asta.health.firebase.model.service.CrashLogService
import fit.asta.health.common.utils.isValidEmail
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AuthService,
    crashLogService: CrashLogService
) : CrashLogViewModel(crashLogService) {

    var uiState = mutableStateOf(UserCred())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            //SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            //SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(SETTINGS_SCREEN, LOGIN_SCREEN)
        }
    }

    fun onForgotPasswordClick() {

        if (!email.isValidEmail()) {
            //SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            //SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }
}

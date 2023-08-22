package fit.asta.health.designsystem.validation.state

import androidx.annotation.StringRes

sealed class ValidationResultState {
    data object Success : ValidationResultState()
    data class Error(@StringRes val id: Int? = null) : ValidationResultState()
}
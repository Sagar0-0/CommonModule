package fit.asta.health.designsystemx.extras.validation.state

import androidx.annotation.StringRes

sealed class ValidationResultState {
    data object Success : ValidationResultState()
    data class Error(@StringRes val id: Int? = null) : ValidationResultState()
}
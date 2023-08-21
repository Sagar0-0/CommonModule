package fit.asta.health.common.validation.state

import androidx.annotation.StringRes

sealed class ValidationResultState {
    object Success : ValidationResultState()
    data class Error(@StringRes val id: Int? = null) : ValidationResultState()
}
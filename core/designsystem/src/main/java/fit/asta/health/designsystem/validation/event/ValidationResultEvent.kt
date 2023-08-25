package fit.asta.health.designsystem.validation.event

import fit.asta.health.common.utils.UiString

sealed class ValidationResultEvent {

    data object Success : ValidationResultEvent()
    data class Error(val message: UiString) : ValidationResultEvent()
}

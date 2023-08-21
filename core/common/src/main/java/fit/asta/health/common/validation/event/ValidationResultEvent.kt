package fit.asta.health.common.validation.event

import fit.asta.health.common.utils.UiString

sealed class ValidationResultEvent {

    object Success : ValidationResultEvent()
    data class Error(val message: UiString) : ValidationResultEvent()
}

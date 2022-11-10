package fit.asta.health.common.validation.event

sealed class ValidationResultEvent {

    object Success : ValidationResultEvent()
}

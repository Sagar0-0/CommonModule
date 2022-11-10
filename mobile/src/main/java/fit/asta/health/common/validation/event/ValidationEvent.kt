package fit.asta.health.common.validation.event

import fit.asta.health.common.validation.state.ValidationState

sealed class ValidationEvent {

    object Submit : ValidationEvent()
    data class TextFieldValueChange(val state: ValidationState) : ValidationEvent()
    data class CheckBoxValueChange(val state: ValidationState) : ValidationEvent()
}

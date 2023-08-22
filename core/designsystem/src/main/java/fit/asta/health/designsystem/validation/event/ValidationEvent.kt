package fit.asta.health.designsystem.validation.event

import fit.asta.health.designsystem.validation.state.ValidationState

sealed class ValidationEvent {

    data object Submit : ValidationEvent()
    data class TextFieldValueChange(val state: ValidationState) : ValidationEvent()
    data class CheckBoxValueChange(val state: ValidationState) : ValidationEvent()
}

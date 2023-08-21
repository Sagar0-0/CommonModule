package fit.asta.health.common.validation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.validation.event.ValidationEvent
import fit.asta.health.common.validation.event.ValidationResultEvent
import fit.asta.health.common.validation.inerfaces.TextFieldId
import fit.asta.health.common.validation.state.ValidationResultState
import fit.asta.health.common.validation.state.ValidationState
import fit.asta.health.common.validation.use_case.ValidateEmail
import fit.asta.health.common.validation.use_case.ValidatePassword
import fit.asta.health.common.validation.use_case.ValidatePhone
import fit.asta.health.common.validation.use_case.ValidateText
import fit.asta.health.common.validation.util.TextFieldType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ValidationViewModel @Inject constructor() : ViewModel() {

    val forms = mutableStateMapOf<TextFieldId, ValidationState>()
    private val validationEventChannel = Channel<ValidationResultEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    fun onEvent(event: ValidationEvent) {

        when (event) {
            is ValidationEvent.TextFieldValueChange -> {

                val validate = when (event.state.type) {
                    TextFieldType.Text -> ValidateText()
                    TextFieldType.Email -> ValidateEmail()
                    TextFieldType.Phone -> ValidatePhone()
                    TextFieldType.Password -> ValidatePassword()
                }

                val result = validate.execute(value = event.state.text.trim())
                forms[event.state.id] = when (result) {
                    is ValidationResultState.Error -> event.state.copy(
                        hasError = true,
                        errorMessageId = result.id
                    )

                    ValidationResultState.Success -> event.state.copy(
                        hasError = false,
                        errorMessageId = null
                    )
                }
            }

            is ValidationEvent.CheckBoxValueChange -> TODO()
            is ValidationEvent.Submit -> isValidForm()
        }
    }

    private fun isValidForm() {

        var isValid = true
        for (state in forms.values) {

            onEvent(ValidationEvent.TextFieldValueChange(state = state))
            if (state.isRequired && state.hasError) {
                isValid = false
            }
        }

        if (isValid) {

            viewModelScope.launch {
                validationEventChannel.send(ValidationResultEvent.Success)
            }
        }
    }
}
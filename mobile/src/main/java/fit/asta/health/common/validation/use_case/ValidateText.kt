package fit.asta.health.common.validation.use_case

import fit.asta.health.R
import fit.asta.health.common.validation.inerfaces.Validate
import fit.asta.health.common.validation.state.ValidationResultState


class ValidateText : Validate<String> {
    override fun execute(value: String): ValidationResultState {
        return when {
            value.isBlank() -> {
                ValidationResultState(
                    isValid = false,
                    errorMessageId = R.string.the_field_can_not_be_blank
                )
            }
            else -> {
                ValidationResultState(isValid = true)
            }
        }
    }
}
package fit.asta.health.common.validation.use_case

import fit.asta.health.R
import fit.asta.health.common.validation.inerfaces.Validate
import fit.asta.health.common.validation.state.ValidationResultState

class ValidatePassword : Validate<String> {

    override fun execute(value: String): ValidationResultState {
        return when {
            value.isBlank() -> ValidationResultState.Error(R.string.the_field_can_not_be_blank)
            value.length < 8 -> ValidationResultState.Error(R.string.the_password_needs_to_consist_of_at_least_8_characters)
            !containLettersAndDigits(value) -> ValidationResultState.Error(R.string.the_password_needs_to_contain_at_least_one_letter_and_digit)
            else -> ValidationResultState.Success
        }
    }

    private fun containLettersAndDigits(value: String) =
        value.any { it.isDigit() } && value.any { it.isLetter() }
}
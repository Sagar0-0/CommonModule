package fit.asta.health.common.validation.use_case

import android.util.Patterns
import fit.asta.health.R
import fit.asta.health.common.validation.inerfaces.Validate
import fit.asta.health.common.validation.state.ValidationResultState


class ValidateEmail : Validate<String> {
    override fun execute(value: String): ValidationResultState {
        return when {
            value.isBlank() -> {
                ValidationResultState(
                    isValid = false,
                    errorMessageId = R.string.the_field_can_not_be_blank
                )
            }
            !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> {
                ValidationResultState(
                    isValid = false,
                    errorMessageId = R.string.that_is_not_a_valid_email
                )
            }
            else -> {
                ValidationResultState(isValid = true)
            }
        }
    }
}
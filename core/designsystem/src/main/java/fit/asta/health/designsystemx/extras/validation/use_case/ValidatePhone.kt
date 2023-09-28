package fit.asta.health.designsystemx.extras.validation.use_case

import android.util.Patterns
import fit.asta.health.designsystemx.extras.validation.inerfaces.Validate
import fit.asta.health.designsystemx.extras.validation.state.ValidationResultState
import fit.asta.health.resources.strings.R

class ValidatePhone : Validate<String> {
    override fun execute(value: String): ValidationResultState {
        return when {
            value.isBlank() -> ValidationResultState.Error(R.string.the_field_can_not_be_blank)
            notMatches(value) -> ValidationResultState.Error(R.string.that_is_not_a_valid_email)
            else -> ValidationResultState.Success
        }
    }

    private fun notMatches(value: String): Boolean {
        return !Patterns.PHONE.matcher(value).matches()
    }
}
package fit.asta.health.designsystemx.extras.validation.use_case

import fit.asta.health.designsystemx.extras.validation.inerfaces.Validate
import fit.asta.health.designsystemx.extras.validation.state.ValidationResultState
import fit.asta.health.resources.strings.R

class ValidateText : Validate<String> {
    override fun execute(value: String): ValidationResultState {
        return when {
            value.isBlank() -> ValidationResultState.Error(R.string.the_field_can_not_be_blank)
            else -> ValidationResultState.Success
        }
    }
}
package fit.asta.health.designsystem.validation.use_case

import fit.asta.health.designsystem.validation.inerfaces.Validate
import fit.asta.health.designsystem.validation.state.ValidationResultState
import fit.asta.health.resources.strings.R

class ValidateCheckBox : Validate<Boolean> {

    override fun execute(value: Boolean): ValidationResultState {
        return when {
            notAccepted(value) -> ValidationResultState.Error(R.string.error_terms_condition_not_accepted)
            else -> ValidationResultState.Success
        }
    }

    private fun notAccepted(accepted: Boolean): Boolean {
        return !accepted
    }
}
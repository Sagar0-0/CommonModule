package fit.asta.health.common.validation.use_case

import fit.asta.health.R
import fit.asta.health.common.validation.inerfaces.Validate
import fit.asta.health.common.validation.state.ValidationResultState


class ValidateCheckBox : Validate<Boolean> {

    override fun execute(value: Boolean): ValidationResultState {
        return when {
            notAccepted(value) -> ValidationResultState(
                isValid = false,
                errorMessageId = R.string.error_terms_condition_not_accepted
            )
            else -> ValidationResultState(isValid = true)
        }
    }

    private fun notAccepted(accepted: Boolean): Boolean {
        return !accepted
    }
}
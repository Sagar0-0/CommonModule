package fit.asta.health.designsystem.extras.validation.inerfaces

import fit.asta.health.designsystem.extras.validation.state.ValidationResultState

interface Validate<in T> {
    fun execute(value: T): ValidationResultState
}
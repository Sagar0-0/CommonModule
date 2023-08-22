package fit.asta.health.designsystem.validation.inerfaces

import fit.asta.health.designsystem.validation.state.ValidationResultState

interface Validate<in T> {
    fun execute(value: T): ValidationResultState
}
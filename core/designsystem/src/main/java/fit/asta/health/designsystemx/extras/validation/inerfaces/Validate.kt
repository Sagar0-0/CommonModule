package fit.asta.health.designsystemx.extras.validation.inerfaces

import fit.asta.health.designsystemx.extras.validation.state.ValidationResultState

interface Validate<in T> {
    fun execute(value: T): ValidationResultState
}
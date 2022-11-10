package fit.asta.health.common.validation.inerfaces

import fit.asta.health.common.validation.state.ValidationResultState

interface Validate<in T> {
    fun execute(value: T): ValidationResultState
}
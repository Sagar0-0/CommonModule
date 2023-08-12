package fit.asta.health.common.utils

import fit.asta.health.R

fun Exception.toStringResId(): Int {
    return when (this.message) {
        else -> R.string.unknown_error
    }
}
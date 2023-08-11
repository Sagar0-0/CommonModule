package fit.asta.health.common.utils

import fit.asta.health.R

fun getStringFromException(e: Exception): Int {
    return when (e.message) {
        else -> R.string.unknown_error
    }
}
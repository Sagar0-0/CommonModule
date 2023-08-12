package fit.asta.health.common.utils

import fit.asta.health.R
import retrofit2.HttpException

fun Exception.toStringResId(): Int {
    return when (this) {
        is HttpException ->{
            R.string.no_transactions_text
        }
        else -> R.string.unknown_error
    }
}
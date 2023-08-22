package fit.asta.health.common.utils

import android.content.Context
import fit.asta.health.core.common.R

fun Context.getErrorMessage(code: Int): String {
    return getString(
        when (code) {
            101 -> {
                R.string.refer_code_not_exist
            }

            102 -> {
                R.string.own_refer_code_error
            }

            103 -> {
                R.string.already_referred_error
            }

            104 -> {
                R.string.referring_my_referred
            }

            else -> {
                R.string.unknown_error
            }
        }
    )
}
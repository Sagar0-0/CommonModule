package fit.asta.health.common.utils

import fit.asta.health.resources.strings.R

class ApiErrorHandler {
    fun fetchStatusMessage(code: Int): Int {
        return when (code) {
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

            105 -> {
                R.string.refund_extended_error
            }

            else -> {
                R.string.unknown_error
            }
        }
    }

    fun fetchExceptionMessage(msg: String): Int {
        val errors = listOf(400, 500, 304, 404, 413)
        return when (errors.find { it.toString() in msg }) {
            400 -> {
                R.string.bad_request
            }

            500 -> {
                R.string.server_error
            }

            304 -> {
                R.string.failed_to_update
            }

            404 -> {
                R.string.no_records_found
            }

            413 -> {
                R.string.file_not_found
            }

            else -> {
                R.string.unknown_error
            }
        }
    }
}
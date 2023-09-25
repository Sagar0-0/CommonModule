package fit.asta.health.common.utils

import fit.asta.health.resources.strings.R

open class ApiErrorHandler {
    open fun <T> fetchStatusMessage(code: Int): ResponseState<T> {
        return ResponseState.ErrorMessage(
            when (code) {
                105 -> {
                    R.string.refund_extended_error
                }

                else -> {
                    R.string.unknown_error
                }
            }
        )
    }

    open fun <T> fetchExceptionMessage(msg: String): ResponseState<T> {
        val errors = listOf(400, 500, 304, 404, 413)
        return ResponseState.ErrorMessage(
            when (errors.find { it.toString() in msg }) {
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
        )
    }
}
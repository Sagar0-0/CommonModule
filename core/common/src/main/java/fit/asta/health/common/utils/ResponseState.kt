package fit.asta.health.common.utils

import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import fit.asta.health.resources.strings.R

data class Response<T>(
    val status: Status,
    val data: T
) {
    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}

sealed interface ResponseState<out T> {
    data class Error(val exception: Throwable) : ResponseState<Nothing>
    data class Success<R>(val data: R) : ResponseState<R>

    data class ErrorMessage(@StringRes val resId: Int) : ResponseState<Nothing>//TODO: WILL BE ERROR
}

suspend fun <T> getResponseState(request: suspend () -> Response<T>): ResponseState<T> {
    return try {
        val response = request()
        when (response.status.code) {
            1 -> {
                ResponseState.Success(response.data)
            }

            else -> {
                ResponseState.ErrorMessage(fetchErrorFromCode(response.status.code))
            }
        }
    } catch (e: Exception) {
        ResponseState.ErrorMessage(fetchErrorFromException(e))
    }
}

//TODO: TO BE REMOVED
suspend fun <T> getResponseState(request: suspend () -> T): ResponseState<T> {
    return try {
        ResponseState.Success(request())
    } catch (e: Exception) {
        ResponseState.ErrorMessage(e.toResIdFromException())
    }
}


fun <T> ResponseState<T>.toUiState(): UiState<T> {
    return when (this) {
        is ResponseState.Success -> {
            UiState.Success(this.data)
        }

        is ResponseState.Error -> {
            UiState.Error(this.exception.toResIdFromException())
        }

        is ResponseState.ErrorMessage -> {
            UiState.Error(this.resId)
        }
    }
}

private fun fetchErrorFromException(e: Throwable): Int {
    return when (e) {
        is MyException -> {
            e.resId
        }

        else -> {
            val errors = listOf(400, 500, 304, 404, 413)
            when (errors.find { it.toString() in e.message!! }) {
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

                else -> {
                    R.string.unknown_error
                }
            }
        }
    }
}

private fun fetchErrorFromCode(code: Int): Int {
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

//TODO: TO BE REMOVED
fun Throwable.toResIdFromException(): Int {
    return when (this) {
        is MyException -> {
            this.resId
        }

        else -> {
            if (this.message!!.contains("400")) {
                R.string.bad_request
            } else if (this.message!!.contains("500")) {
                R.string.server_error
            } else if (this.message!!.contains("304")) {
                R.string.failed_to_update
            } else if (this.message!!.contains("404")) {
                R.string.no_records_found
            } else {
                R.string.unknown_error
            }
        }
    }
}
package fit.asta.health.common.utils

import android.os.Parcelable
import android.util.Log
import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import fit.asta.health.resources.strings.R
import kotlinx.parcelize.Parcelize

const val TAG = "RES"
const val SUCCESS_STATUS_CODE = 0

data class Response<T>(
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("data")
    val data: T
) {
    @Parcelize
    data class Status(
        @SerializedName("code")
        val code: Int = SUCCESS_STATUS_CODE,
        @SerializedName("msg")
        val msg: String = "",
        @SerializedName("retry")
        val retry: Boolean = false,
        @SerializedName("err")
        val error: String = "",
    ) : Parcelable
}

sealed interface ResponseState<out T> {
    data class Success<R>(val data: R) : ResponseState<R>
    data object NoInternet : ResponseState<Nothing>
    data class ErrorMessage(@StringRes val resId: Int) : ResponseState<Nothing>
    data class ErrorRetry(@StringRes val resId: Int) : ResponseState<Nothing>
}

suspend fun <T> getApiResponseState(
    onSuccess: suspend (Response<T>) -> Unit = {},
    onFailure: suspend (Exception) -> Unit = {},
    errorHandler: ApiErrorHandler = ApiErrorHandler(),
    request: suspend () -> Response<T>
): ResponseState<T> {
    return try {
        val response = request()
        onSuccess(response)

        Log.e("TAG", "ResponseState: $response")
        when (response.status.code) {
            SUCCESS_STATUS_CODE -> {
                if (response.data != null) {
                    ResponseState.Success(response.data)
                } else {//Rare case: Null Data and Success Status code
                    ResponseState.ErrorMessage(R.string.null_data_error)
                }
            }

            else -> {
                errorHandler.fetchStatusCodeMessage(response.status)
            }
        }
    } catch (e: NoInternetException) {
        ResponseState.NoInternet
    } catch (e: Exception) {
        onFailure(e)
        errorHandler.fetchHTTPExceptionMessage(e.message ?: "")
    }
}

suspend fun <T> getResponseState(
    onSuccess: suspend () -> Unit = {},
    onFailure: suspend (Exception) -> Unit = {},
    errorHandler: ApiErrorHandler = ApiErrorHandler(),
    request: suspend () -> T
): ResponseState<T> {
    return try {
        val res = request()
        onSuccess()
        ResponseState.Success(res)
    } catch (e: NoInternetException) {
        ResponseState.NoInternet
    } catch (e: Exception) {
        onFailure(e)
        errorHandler.fetchHTTPExceptionMessage(e.message ?: "")
    }
}


fun <T> ResponseState<T>.toUiState(): UiState<T> {
    return when (this) {
        is ResponseState.Success -> {
            UiState.Success(this.data)
        }

        is ResponseState.ErrorMessage -> {
            UiState.ErrorMessage(this.resId)
        }

        is ResponseState.ErrorRetry -> {
            UiState.ErrorRetry(this.resId)
        }

        else -> {
            UiState.NoInternet
        }
    }
}

fun <T, R> ResponseState<T>.map(mapper: (T) -> R): ResponseState<R> {
    return when (this) {
        is ResponseState.Success -> {
            ResponseState.Success(mapper(this.data))
        }

        is ResponseState.NoInternet -> {
            ResponseState.NoInternet
        }

        is ResponseState.ErrorMessage -> {
            ResponseState.ErrorMessage(this.resId)
        }

        is ResponseState.ErrorRetry -> {
            ResponseState.ErrorRetry(this.resId)
        }
    }
}
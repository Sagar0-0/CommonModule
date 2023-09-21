package fit.asta.health.common.utils

import android.os.Parcelable
import android.util.Log
import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import fit.asta.health.resources.strings.R
import kotlinx.parcelize.Parcelize

const val TAG = "RES"
const val SUCCESS_STATUS_CODE = 200

data class Response<T>(
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("data")
    val data: T
) {
    @Parcelize
    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    ) : Parcelable
}

sealed interface ResponseState<out T> {
    data class Success<R>(val data: R) : ResponseState<R>
    data class ErrorMessage(@StringRes val resId: Int) : ResponseState<Nothing>
    data class ErrorRetry(@StringRes val resId: Int) : ResponseState<Nothing>

}

suspend fun <T> getApiResponseState(
    onSuccess: suspend () -> Unit = {},
    onFailure: suspend (Exception) -> Unit = {},
    errorHandler: ApiErrorHandler = ApiErrorHandler(),
    request: suspend () -> Response<T>
): ResponseState<T> {
    return try {
        val response = request()
        onSuccess()
        Log.e(TAG, "getResponseState: ${response.status.code}: ${response.status.msg}")
        when (response.status.code) {
            SUCCESS_STATUS_CODE -> {
                if (response.data != null) {
                    ResponseState.Success(response.data)
                } else {//Rare case: Null Data and Success Status code
                    ResponseState.ErrorMessage(R.string.null_data_error)
                }
            }

            else -> {//TODO: Define all cases for ErrorMessage and ErrorRetry
                ResponseState.ErrorMessage(errorHandler.fetchStatusMessage(response.status.code))
            }
        }
    } catch (e: Exception) {
        onFailure(e)
        Log.e(TAG, "getResponseState Exception:  $e")
        when (e) {
            is MyException -> {
                ResponseState.ErrorMessage(e.resId)
            }

            else -> {
                ResponseState.ErrorMessage(errorHandler.fetchExceptionMessage(e.message ?: ""))
            }
        }
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
    } catch (e: Exception) {
        onFailure(e)
        Log.e(TAG, "getResponseState: $e")
        ResponseState.ErrorMessage(errorHandler.fetchExceptionMessage(e.message ?: ""))
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
    }
}
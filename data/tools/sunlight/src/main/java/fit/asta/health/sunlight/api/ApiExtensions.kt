package fit.asta.health.sunlight.api

import android.util.Log
import fit.asta.health.common.utils.ApiErrorHandler
import fit.asta.health.common.utils.NoInternetException
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.SUCCESS_STATUS_CODE
import kotlinx.coroutines.flow.FlowCollector
//import fit.asta.health.resources.strings.R as StrR


internal suspend fun <T> FlowCollector<ResponseState<T>>.emitResponse(
    onSuccess: suspend () -> Unit = {},
    onFailure: suspend (Exception) -> Unit = {},
    errorHandler: ApiErrorHandler = ApiErrorHandler(),
    request: suspend () -> Response<T>
) {
    Log.d("inEmitter", "emitResponse: response")

    try {
        val response = request()
        Log.d("inEmitter", "emitResponse: response")

        onSuccess()
        when (response.status.code) {
            SUCCESS_STATUS_CODE -> {
                if (response.data != null) {
                    Log.d("inEmitter", "emitResponse: success")
                    emit(ResponseState.Success(response.data))
                } else {
                    Log.d("inEmitter", "null: success")
                    emit(errorHandler.fetchStatusCodeMessage(response.status))
                    //Rare case: Null Data and Success Status code
//                    emit(ResponseState.ErrorMessage(StrR.string.null_data_error))
//                    emit(ResponseState.ErrorMessage(""))
                }
            }

            else -> {
                emit(errorHandler.fetchStatusCodeMessage(response.status))
            }
        }
    } catch (e: NoInternetException) {
        Log.d("inEmitter", "emitResponse: error")
        emit(ResponseState.NoInternet)
    } catch (e: Exception) {
        Log.d("inEmitter", "emitResponse: ${e.message}")
        onFailure(e)
        emit(errorHandler.fetchHTTPExceptionMessage(e.message ?: ""))
    }
}



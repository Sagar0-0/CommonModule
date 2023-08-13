package fit.asta.health.common.utils

import android.util.Log


fun <T> T.toResponseState(): ResponseState<T> {
    return try {
        ResponseState.Success(this)
    } catch (e: Exception) {
        Log.e("ERR", "toResponseState: $e")
        ResponseState.Error(e)
    }
}

suspend fun <T> getResponseState(request:suspend ()->T) : ResponseState<T> {
    return try{
        ResponseState.Success(request())
    }catch (e:Exception){
        ResponseState.Error(e)
    }
}

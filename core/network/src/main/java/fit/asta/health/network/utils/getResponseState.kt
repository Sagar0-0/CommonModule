package fit.asta.health.network.utils

suspend fun <T> getResponseState(request: suspend () -> T): fit.asta.health.common.utils.ResponseState<T> {
    return try {
        fit.asta.health.common.utils.ResponseState.Success(request())
    } catch (e: Exception) {
        fit.asta.health.common.utils.ResponseState.Error(e)
    }
}

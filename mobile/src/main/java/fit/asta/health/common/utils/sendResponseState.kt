package fit.asta.health.common.utils

fun <T> T.sendResponseState() : ResponseState<T> {
    return try{
        ResponseState.Success(this)
    }catch (e: Exception){
        ResponseState.Error(e)
    }
}
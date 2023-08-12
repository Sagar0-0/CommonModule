package fit.asta.health.common.utils

fun <T> T.toResponseState() : ResponseState<T> {
    return try{
        ResponseState.Success(this)
    }catch (e: Exception){
        ResponseState.Error(e)
    }
}
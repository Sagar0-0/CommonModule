package fit.asta.health.common.utils

fun <T> ResponseState<T>.mapToUiState() : UiState<T> {
    return when(this){
        is ResponseState.Success->{
            UiState.Success(this.data)
        }
        is ResponseState.Error->{
            UiState.Error(getStringFromException(this.error))
        }
        else->{ UiState.Idle }
    }
}
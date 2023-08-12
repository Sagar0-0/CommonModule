package fit.asta.health.common.utils

fun <T> ResponseState<T>.toUiState() : UiState<T> {
    return when(this){
        is ResponseState.Success->{
            UiState.Success(this.data)
        }
        is ResponseState.Error->{
            UiState.Error(this.exception.toStringResId())
        }
        else->{ UiState.Idle }
    }
}
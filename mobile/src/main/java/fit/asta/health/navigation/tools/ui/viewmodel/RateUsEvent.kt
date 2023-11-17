package fit.asta.health.navigation.tools.ui.viewmodel

sealed class RateUsEvent {

    //data class InAppReviewRequested(val reviewInfo: ReviewInfo) : HomeEvent()
    data class Error(val throwable: Throwable) : RateUsEvent()
    data object InAppReviewRequested : RateUsEvent()
    data object InAppReviewCompleted : RateUsEvent()
    data object Session : RateUsEvent()
    data object NoOp : RateUsEvent()
}
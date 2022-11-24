package fit.asta.health.navigation.home.viewmodel

sealed class RateUsEvent {

    //data class InAppReviewRequested(val reviewInfo: ReviewInfo) : HomeEvent()
    data class Error(val throwable: Throwable) : RateUsEvent()
    object InAppReviewRequested : RateUsEvent()
    object InAppReviewCompleted : RateUsEvent()
    object Session : RateUsEvent()
    object NoOp : RateUsEvent()
}
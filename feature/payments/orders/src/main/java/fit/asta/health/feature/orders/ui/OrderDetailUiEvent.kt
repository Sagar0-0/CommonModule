package fit.asta.health.feature.orders.ui

sealed interface OrderDetailUiEvent {
    data object Back : OrderDetailUiEvent
}

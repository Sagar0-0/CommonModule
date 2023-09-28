package fit.asta.health.subscription.view

sealed class SubscriptionScreen(val route: String) {
    data object Plans : SubscriptionScreen("sub_plans")
}

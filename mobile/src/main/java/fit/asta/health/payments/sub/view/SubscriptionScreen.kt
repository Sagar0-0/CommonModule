package fit.asta.health.payments.sub.view

sealed class SubscriptionScreen(val route: String) {
    object Plans : SubscriptionScreen("sub_plans")
}

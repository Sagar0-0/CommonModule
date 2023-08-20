package com.example.subscription.ui.view

sealed class SubscriptionScreen(val route: String) {
    data object Plans : SubscriptionScreen("sub_plans")
}

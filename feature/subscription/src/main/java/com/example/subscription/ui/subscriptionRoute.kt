package com.example.subscription.ui

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.payment.PaymentActivity
import com.example.subscription.ui.view.SubscriptionPlansUi
import com.example.subscription.ui.vm.SubscriptionViewModel

private const val SUBSCRIPTION_GRAPH_ROUTE = "graph_subscription"
fun NavController.navigateToSubscription(navOptions: NavOptions? = null) {
    this.navigate(SUBSCRIPTION_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.subscriptionRoute(navController: NavHostController) {
    navigation(
        route = SUBSCRIPTION_GRAPH_ROUTE,
        startDestination = com.example.subscription.ui.view.SubscriptionScreen.Plans.route
    ) {
        composable(com.example.subscription.ui.view.SubscriptionScreen.Plans.route) {
            val subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
            val state = subscriptionViewModel.state.collectAsStateWithLifecycle()
            val context = LocalContext.current
            SubscriptionPlansUi(
                state = state.value,
                onBackPress = navController::navigateUp,
                onTryAgain = subscriptionViewModel::getData,
                onClick = { orderRequest ->
                    PaymentActivity.launch(context, orderRequest) {
                        subscriptionViewModel.getData()
                    }
                }
            )
        }
    }

}
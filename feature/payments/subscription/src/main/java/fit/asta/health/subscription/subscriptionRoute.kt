package fit.asta.health.subscription

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.subscription.remote.model.Offer
import fit.asta.health.subscription.view.SubscriptionPlansUi
import fit.asta.health.subscription.view.SubscriptionScreen
import fit.asta.health.subscription.vm.SubscriptionViewModel

private const val SUBSCRIPTION_GRAPH_ROUTE = "graph_subscription"
private const val SUBSCRIPTION_DURATION_ROUTE = "graph_duration_route/{subType}"
private const val SUBSCRIPTION_FINAL_SCREEN = "graph_final_route/{offer}"
fun NavController.navigateToSubscriptionDurations(subType: String) {
    this.navigate(SUBSCRIPTION_DURATION_ROUTE.replace("subType", subType))
}

fun NavController.navigateWithOffer(offer: Offer) {
    this.navigate(SUBSCRIPTION_FINAL_SCREEN.replace("offer", "offerJson"))
}

fun NavController.navigateToSubscription(navOptions: NavOptions? = null) {
    this.navigate(SUBSCRIPTION_GRAPH_ROUTE, navOptions)
}


fun NavGraphBuilder.subscriptionRoute(
    onBackPress: () -> Unit,
    onLaunchPayments: (Context, OrderRequest, onSuccess: () -> Unit) -> Unit
) {
    navigation(
        route = SUBSCRIPTION_GRAPH_ROUTE,
        startDestination = SubscriptionScreen.Plans.route
    ) {
        composable(SubscriptionScreen.Plans.route) {
            val context = LocalContext.current
            val subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
            LaunchedEffect(key1 = Unit, block = { subscriptionViewModel.getData() })

            val state by subscriptionViewModel.state.collectAsStateWithLifecycle()
            SubscriptionPlansUi(
                state = state,
                onBackPress = onBackPress,
                onTryAgain = subscriptionViewModel::getData,
                onClick = { orderRequest ->
                    onLaunchPayments(context, orderRequest) {
                        subscriptionViewModel.getData()
                    }
                }
            )
        }

        composable(SUBSCRIPTION_DURATION_ROUTE) {
            val addType = it.arguments?.getString("subType")!!
//            SubscriptionDurationsScreen(
//                planSubscriptionPlanCategory = ,
//                onBack = {
//                    onBackPress()
//                },
//                onClick = { subType, durType ->
//
//                }
//            )
        }
    }

}
package fit.asta.health.subscription

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import fit.asta.health.payment.remote.model.OrderRequest

private const val SUBSCRIPTION_GRAPH_ROUTE = "graph_subscription"

fun NavController.navigateToSubscription(navOptions: NavOptions? = null) {
    this.navigate(SUBSCRIPTION_GRAPH_ROUTE, navOptions)
}


fun NavGraphBuilder.subscriptionRoute(
    onBackPress: () -> Unit,
    onLaunchPayments: (Context, OrderRequest, onSuccess: () -> Unit) -> Unit
) {
//    navigation(
//        route = SUBSCRIPTION_GRAPH_ROUTE,
//        startDestination = SubscriptionScreen.Plans.route
//    ) {
//        composable(SubscriptionScreen.Plans.route) {
//            val context = LocalContext.current
//            val subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
//            LaunchedEffect(key1 = Unit, block = { subscriptionViewModel.getData() })
//
//            val state by subscriptionViewModel.state.collectAsStateWithLifecycle()
//            SubscriptionPlansUi(
//                state = state,
//                onBackPress = onBackPress,
//                onTryAgain = subscriptionViewModel::getData,
//                onClick = { orderRequest ->
//                    onLaunchPayments(context, orderRequest) {
//                        subscriptionViewModel.getData()
//                    }
//                }
//            )
//        }
//
//    }

}
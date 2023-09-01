package fit.asta.health.subscription

import android.content.Context
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
import fit.asta.health.subscription.view.SubscriptionPlansUi
import fit.asta.health.subscription.view.SubscriptionScreen
import fit.asta.health.subscription.vm.SubscriptionViewModel

private const val SUBSCRIPTION_GRAPH_ROUTE = "graph_subscription"
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
            val state by subscriptionViewModel.state.collectAsStateWithLifecycle()
            val isFCMUploaded by subscriptionViewModel.isFCMTokenUploaded.collectAsStateWithLifecycle()
            SubscriptionPlansUi(
                state = state,
                onBackPress = onBackPress,
                onTryAgain = subscriptionViewModel::getData,
                onClick = { orderRequest ->
                    if(isFCMUploaded){
                        onLaunchPayments(context, orderRequest) {
                            subscriptionViewModel.getData()
                        }
                    }else{
                        //TODO: IF NOT UPLOADED THEN WHAT?
                    }
                }
            )
        }
    }

}
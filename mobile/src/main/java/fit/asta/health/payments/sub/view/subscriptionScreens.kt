package fit.asta.health.payments.sub.view

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.main.Graph
import fit.asta.health.payments.pay.PaymentActivity
import fit.asta.health.payments.sub.vm.SubscriptionViewModel

fun NavGraphBuilder.subscriptionScreens(navController: NavHostController) {
    navigation(
        route = Graph.Subscription.route,
        startDestination = SubscriptionScreen.Plans.route
    ) {
        composable(SubscriptionScreen.Plans.route) {
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
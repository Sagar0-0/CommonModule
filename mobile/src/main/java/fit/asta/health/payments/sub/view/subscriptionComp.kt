package fit.asta.health.payments.sub.view

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fit.asta.health.main.Graph
import fit.asta.health.payments.pay.PaymentActivity
import fit.asta.health.payments.sub.vm.SubscriptionViewModel

fun NavGraphBuilder.subscriptionComp(navController: NavHostController) {
    composable(Graph.Subscription.route) {
        val subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
        val state = subscriptionViewModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current
        SubscriptionScreen(
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
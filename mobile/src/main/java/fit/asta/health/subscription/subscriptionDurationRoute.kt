package fit.asta.health.subscription

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.subscription.view.SubscriptionDurationsScreen


private const val SUBSCRIPTION_DURATION_ROUTE = "graph_duration_route"

fun NavController.navigateToSubscriptionDurations(categoryId: String) {
    this.navigate("$SUBSCRIPTION_DURATION_ROUTE?categoryId=$categoryId")
}

fun NavGraphBuilder.subscriptionDurationRoute(navController: NavController) {
    composable(
        route = "$SUBSCRIPTION_DURATION_ROUTE?categoryId={categoryId}",
        arguments = listOf(
            navArgument("categoryId") {
                defaultValue = ""
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        val subscriptionViewModel: SubscriptionViewModel =
            it.sharedViewModel(navController = navController)
        val subscriptionDurationState =
            subscriptionViewModel.subscriptionDurationDataState.collectAsStateWithLifecycle().value

        val categoryId = it.arguments?.getString("categoryId")!!

        AppUiStateHandler(
            uiState = subscriptionDurationState,
            onSuccess = { data ->
                SubscriptionDurationsScreen(
                    data = data,
                    onBack = navController::popBackStack,
                    onNavigateToFinalPayment = { productId ->
                        navController.navigateToFinalPaymentScreen(categoryId, productId)
                    }
                )
            },
            onIdle = {
                LaunchedEffect(Unit) {
                    subscriptionViewModel.getSubscriptionDurationData(categoryId)
                }
            },
            onErrorRetry = {
                subscriptionViewModel.getSubscriptionDurationData(categoryId)
            },
            onErrorMessage = {
                navController.popBackStack()
            }
        )

    }
}
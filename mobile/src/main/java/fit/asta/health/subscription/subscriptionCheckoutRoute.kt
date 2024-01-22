package fit.asta.health.subscription

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.main.view.HOME_ROUTE
import fit.asta.health.payment.PaymentActivity

private const val SUBSCRIPTION_CHECKOUT_SCREEN = "graph_final_route"

fun NavController.navigateToFinalPaymentScreen(categoryId: String, productId: String) {
    this.navigate(
        SUBSCRIPTION_CHECKOUT_SCREEN + "?categoryId=${categoryId}&productId=${productId}"
    )
}

fun NavGraphBuilder.subscriptionCheckoutRoute(navController: NavController) {
    composable(
        route = "$SUBSCRIPTION_CHECKOUT_SCREEN?categoryId={categoryId}&productId={productId}",
        arguments = listOf(
            navArgument("categoryId") {
                defaultValue = ""
                type = NavType.StringType
                nullable = false
            },
            navArgument("productId") {
                defaultValue = ""
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        val categoryId = it.arguments?.getString("categoryId")!!
        val productId = it.arguments?.getString("productId")!!

        val subscriptionViewModel: SubscriptionViewModel =
            it.sharedViewModel(navController = navController)
        LaunchedEffect(
            key1 = Unit,
            block = {
                subscriptionViewModel.getSubscriptionFinalAmountData(categoryId, productId)
                subscriptionViewModel.getWalletData()
            }
        )
        val context = LocalContext.current

        val walletResponseState by
        subscriptionViewModel.walletResponseState.collectAsStateWithLifecycle()
        val couponResponseState by
        subscriptionViewModel.couponResponseState.collectAsStateWithLifecycle()
        val subscriptionFinalPaymentState by
        subscriptionViewModel.subscriptionFinalPaymentState.collectAsStateWithLifecycle()

        AppUiStateHandler(
            uiState = subscriptionFinalPaymentState,
            onRetry = {
                subscriptionViewModel.getSubscriptionFinalAmountData(categoryId, productId)
            },
            onErrorMessage = {
                navController.popBackStack()
            }
        ) { data ->
            SubscriptionCheckoutScreen(
                subscriptionCheckoutScreenData = SubscriptionCheckoutScreenData(
                    planMRP = data.details.amt,
                    offerAmount = data.details.ofrAmt,
                    imageUrl = data.details.url,
                    planName = data.details.ttl,
                    planDesc = data.details.sub,
                    offerCode = data.details.offerCode,
                    categoryId = categoryId,
                    productId = productId,
                    featuresList = data.features.map { feature ->
                        feature.ttl
                    }
                ),
                couponResponseState = couponResponseState,
                walletResponseState = walletResponseState
            ) { event ->
                when (event) {
                    is BuyScreenEvent.ApplyCouponCode -> {
                        subscriptionViewModel.applyCouponCode(event.code, event.productMRP)
                    }

                    BuyScreenEvent.BACK -> {
                        navController.popBackStack()
                    }

                    BuyScreenEvent.FetchWalletData -> {
                        subscriptionViewModel.getWalletData()
                    }

                    is BuyScreenEvent.ProceedToBuy -> {
                        PaymentActivity.launch(
                            context = context,
                            orderRequest = event.orderRequest
                        ) {
                            navController.navigate(HOME_ROUTE) {
                                popUpTo(HOME_ROUTE) {
                                    inclusive = false
                                }
                            }
                        }
                    }

                    BuyScreenEvent.ResetCouponState -> {
                        subscriptionViewModel.resetCouponState()
                    }
                }
            }
        }
    }
}
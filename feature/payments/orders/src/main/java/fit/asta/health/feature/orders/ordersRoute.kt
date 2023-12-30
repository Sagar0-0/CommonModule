package fit.asta.health.feature.orders

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.orders.remote.OrderId
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.feature.orders.ui.OrderDetailScreen
import fit.asta.health.feature.orders.ui.OrdersScreen
import fit.asta.health.feature.orders.vm.OrdersViewModel

private const val ORDERS_GRAPH_ROUTE = "graph_orders"
private const val ORDERS_LIST_ROUTE = "graph_list_order"
private const val ORDERS_DETAIL_ROUTE = "graph_detail_order"

fun NavController.navigateToOrders(navOptions: NavOptions? = null) {
    this.navigate(ORDERS_GRAPH_ROUTE, navOptions)
}

fun NavController.navigateToOrderDetailsPage(ordersViewModel: OrdersViewModel, orderId: OrderId) {
    ordersViewModel.getOrderDetail(orderId)
    this.navigate(
        ORDERS_DETAIL_ROUTE
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.ordersRoute(onBack: () -> Unit) {
    composable(ORDERS_GRAPH_ROUTE) {
        val ordersViewModel: OrdersViewModel = hiltViewModel()
        val context = LocalContext.current
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = ORDERS_LIST_ROUTE) {
            composable(route = ORDERS_LIST_ROUTE) {
                LaunchedEffect(Unit) { ordersViewModel.getOrders() }
                val ordersState = ordersViewModel.ordersState.collectAsStateWithLifecycle().value

                AppScaffold(
                    topBar = {
                        AppTopBar(
                            title = "Orders",
                            onBack = onBack
                        )
                    }
                ) { padding ->
                    when (ordersState) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .padding(padding)
                                    .fillMaxSize()
                            ) {
                                AppDotTypingAnimation()
                            }
                        }

                        is UiState.Success -> {
                            OrdersScreen(
                                modifier = Modifier.padding(padding),
                                orders = ordersState.data
                            ) { orderId ->
                                navController.navigateToOrderDetailsPage(ordersViewModel, orderId)
                            }
                        }

                        is UiState.ErrorMessage -> {
                            Toast.makeText(
                                context,
                                ordersState.resId.toStringFromResId(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.ErrorRetry -> {
                            AppInternetErrorDialog()
                        }

                        else -> {}
                    }
                }
            }

            composable(route = ORDERS_DETAIL_ROUTE) {

                val orderDetailDataState =
                    ordersViewModel.orderDetailState.collectAsStateWithLifecycle().value
                AppScaffold(
                    topBar = {
                        AppTopBar(
                            title = "Order Details",
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                ) { padding ->
                    when (orderDetailDataState) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .padding(padding)
                                    .fillMaxSize()
                            ) {
                                AppDotTypingAnimation()
                            }
                        }

                        is UiState.Success -> {
                            OrderDetailScreen(
                                modifier = Modifier.padding(padding),
                                orderData = orderDetailDataState.data
                            ) { _ ->

                            }
                        }

                        is UiState.ErrorMessage -> {
                            Toast.makeText(
                                context,
                                orderDetailDataState.resId.toStringFromResId(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.ErrorRetry -> {
                            AppInternetErrorDialog()
                        }

                        else -> {}
                    }
                }
            }
        }

    }
}

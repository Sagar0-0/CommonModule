package fit.asta.health.feature.orders

import android.widget.Toast
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
import com.squareup.moshi.Moshi
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.feature.orders.ui.OrderDetailScreen
import fit.asta.health.feature.orders.ui.OrderDetailUiEvent
import fit.asta.health.feature.orders.ui.OrdersScreen
import fit.asta.health.feature.orders.vm.OrdersViewModel

private const val ORDERS_GRAPH_ROUTE = "graph_orders"
private const val ORDERS_LIST_ROUTE = "graph_list_order"
private const val ORDERS_DETAIL_ROUTE = "graph_detail_order/order={order}"

fun NavController.navigateToOrders(navOptions: NavOptions? = null) {
    this.navigate(ORDERS_GRAPH_ROUTE, navOptions)
}

fun NavController.navigateToOrderDetailsPage(orderData: OrderData) {
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(OrderData::class.java).lenient()
    val orderJson = jsonAdapter.toJson(orderData)

    this.navigate(
        ORDERS_DETAIL_ROUTE.replace("{order}", orderJson)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.ordersRoute(onBack: () -> Unit) {
    composable(ORDERS_GRAPH_ROUTE) {
        val ordersViewModel: OrdersViewModel = hiltViewModel()
        LaunchedEffect(Unit) { ordersViewModel.getOrders() }
        val ordersState = ordersViewModel.ordersState.collectAsStateWithLifecycle().value
        val context = LocalContext.current
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ORDERS_LIST_ROUTE) {
            composable(route = ORDERS_LIST_ROUTE) {
                AppScaffold(
                    topBar = {
                        AppTopBar(
                            title = "Orders",
                            onBack = onBack
                        )
                    }
                ) { padd ->
                    when (ordersState) {
                        is UiState.Loading -> {
                            AppDotTypingAnimation(modifier = Modifier.padding(padd))
                        }

                        is UiState.Success -> {
                            OrdersScreen(
                                modifier = Modifier.padding(padd),
                                orders = ordersState.data
                            ) {
                                navController.navigateToOrderDetailsPage(it)
                            }
                        }

                        is UiState.ErrorMessage -> {
                            Toast.makeText(
                                context,
                                ordersState.resId.toStringFromResId(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        is UiState.ErrorRetry -> {
                            AppInternetErrorDialog()
                        }

                        else -> {}
                    }
                }
            }

            composable(route = ORDERS_DETAIL_ROUTE) {
                val orderJson = it.arguments?.getString("order")!!
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter(OrderData::class.java).lenient()
                val orderData = jsonAdapter.fromJson(orderJson)!!

                OrderDetailScreen(orderData) { event ->
                    when (event) {
                        is OrderDetailUiEvent.Back -> {
                            navController.popBackStack()
                        }
                    }

                }
            }
        }

    }
}

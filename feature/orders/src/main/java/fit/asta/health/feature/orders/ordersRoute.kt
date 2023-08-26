package fit.asta.health.feature.orders

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.orders.remote.model.OrdersDTO
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.feature.orders.vm.OrdersViewModel

private const val ORDERS_GRAPH_ROUTE = "graph_orders"

fun NavController.navigateToOrders(navOptions: NavOptions? = null) {
    this.navigate(ORDERS_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.ordersRoute(onBack: () -> Unit) {
    composable(ORDERS_GRAPH_ROUTE) {
        val ordersViewModel: OrdersViewModel = hiltViewModel()
        LaunchedEffect(Unit) { ordersViewModel.getOrders() }
        val ordersState by ordersViewModel.ordersState.collectAsStateWithLifecycle()
        OrdersScreen(ordersState)
    }
}

@Composable
fun OrdersScreen(ordersState: UiState<OrdersDTO>) {
    when (ordersState) {
        is UiState.Loading -> {
            LoadingAnimation()
        }

        is UiState.Success -> {
            LazyColumn {

            }
        }

        is UiState.Error -> {
            AppErrorScreen()
        }

        else -> {}
    }
}
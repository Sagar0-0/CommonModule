package fit.asta.health.feature.orders

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.texts.TitleTexts
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
        OrdersScreen(ordersState, onBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(ordersState: UiState<List<OrderData>>, onBack: () -> Unit) {
    val context = LocalContext.current
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
                LazyColumn(modifier = Modifier.padding(padd)) {
                    items(ordersState.data) {
                        TitleTexts.Level2(text = it.orderId)
                    }
                }
            }

            is UiState.ErrorMessage -> {
                Toast.makeText(context, ordersState.resId.toStringFromResId(), Toast.LENGTH_SHORT)
                    .show()
            }

            is UiState.ErrorRetry -> {
                AppErrorScreen()
            }

            else -> {}
        }
    }

}
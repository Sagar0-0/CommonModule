package fit.asta.health.wallet

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.wallet.view.WalletScreenUi
import fit.asta.health.wallet.vm.WalletViewModel

private const val WALLET_GRAPH_ROUTE = "graph_wallet"

fun NavController.navigateToWallet(navOptions: NavOptions? = null) {
    this.navigate(WALLET_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.walletRoute(navController: NavHostController) {
    composable(WALLET_GRAPH_ROUTE) {
        val walletViewModel: WalletViewModel = hiltViewModel()
        val state = walletViewModel.state.collectAsStateWithLifecycle()
        WalletScreenUi(
            walletDataState = state.value,
            onBackPress = navController::navigateUp,
            onTryAgain = walletViewModel::getData
        )
    }
}
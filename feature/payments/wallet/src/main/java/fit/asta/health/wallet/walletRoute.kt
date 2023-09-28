package fit.asta.health.wallet

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.wallet.view.WalletScreenUi
import fit.asta.health.wallet.vm.WalletViewModel

private const val WALLET_GRAPH_ROUTE = "graph_wallet"

fun NavController.navigateToWallet(navOptions: NavOptions? = null) {
    this.navigate(WALLET_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.walletRoute(onBackPress: () -> Unit) {
    composable(WALLET_GRAPH_ROUTE) {
        val walletViewModel: WalletViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            walletViewModel.getData()
        }

        val state by walletViewModel.state.collectAsStateWithLifecycle()
        WalletScreenUi(
            walletDataState = state,
            onBackPress = onBackPress,
            onTryAgain = walletViewModel::getData
        )
    }
}
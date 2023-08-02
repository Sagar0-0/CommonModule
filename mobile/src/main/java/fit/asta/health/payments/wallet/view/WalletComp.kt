package fit.asta.health.payments.wallet.view

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fit.asta.health.main.Graph
import fit.asta.health.payments.wallet.vm.WalletViewModel

fun NavGraphBuilder.walletComp(navController: NavHostController) {
    composable(Graph.Wallet.route) {
        val walletViewModel: WalletViewModel = hiltViewModel()
        val state = walletViewModel.state.collectAsStateWithLifecycle()
        WalletScreen(
            walletDataState = state.value,
            onBackPress = navController::navigateUp,
            onTryAgain = walletViewModel::getData
        )
    }
}
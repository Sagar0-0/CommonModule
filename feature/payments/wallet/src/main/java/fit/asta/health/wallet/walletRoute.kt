package fit.asta.health.wallet

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.background.appRememberBottomSheetScaffoldState
import fit.asta.health.wallet.view.WalletScreenUi
import fit.asta.health.wallet.vm.WalletViewModel

private const val WALLET_GRAPH_ROUTE = "graph_wallet"

fun NavController.navigateToWallet(navOptions: NavOptions? = null) {
    this.navigate(WALLET_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.walletRoute(
    onProceedToAdd: (context: Context, amount: String, onPaymentSuccess: () -> Unit, onPaymentFailure: () -> Unit) -> Unit,
    onBackPress: () -> Unit
) {
    composable(WALLET_GRAPH_ROUTE) {
        val context = LocalContext.current
        val walletViewModel: WalletViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            walletViewModel.getData()
            walletViewModel.getOffersData()
        }

        val walletDataState = walletViewModel.state.collectAsStateWithLifecycle().value

        val scaffoldState = appRememberBottomSheetScaffoldState()
        AppBottomSheetScaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppTopBar(title = "Wallet", onBack = onBackPress)
            },
            sheetPeekHeight = 0.dp,
            sheetSwipeEnabled = false,
            sheetDragHandle = null,
            sheetContent = {}
        ) { paddingValues ->
            when (walletDataState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        AppDotTypingAnimation()
                    }
                }

                is UiState.ErrorMessage -> {
                    AppErrorScreen(
                        modifier = Modifier.padding(paddingValues),
                        text = walletDataState.resId.toStringFromResId(),
                        onTryAgain = walletViewModel::getData
                    )
                }

                is UiState.Success -> {
                    WalletScreenUi(
                        modifier = Modifier.padding(paddingValues),
                        walletData = walletDataState.data
                    ) { amount ->
                        if (amount != "0") {
                            onProceedToAdd(
                                context,
                                amount,
                                {
                                    walletViewModel.getData()
                                },
                                {
                                    Toast.makeText(
                                        context,
                                        "Payment Failed, try again later!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        } else {
                            Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                else -> {}
            }
        }
    }
}
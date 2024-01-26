package fit.asta.health.referral

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.referral.view.NewReferralDesign
import fit.asta.health.referral.view.ReferralDestination
import fit.asta.health.referral.vm.ReferralViewModel

private const val REFERRAL_GRAPH_ROUTE = "graph_referral"

fun NavController.navigateToReferral(navOptions: NavOptions? = null) {
    this.navigate(REFERRAL_GRAPH_ROUTE, navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.referralRoute(onBackPress: () -> Unit, shareReferralCode: (String) -> Unit) {
    navigation(
        route = REFERRAL_GRAPH_ROUTE,
        startDestination = ReferralDestination.Share.route
    ) {
        composable(ReferralDestination.Share.route) {
            val referralViewModel: ReferralViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                referralViewModel.getData()
            }
            val context = LocalContext.current
            val referralDataState = referralViewModel.state.collectAsStateWithLifecycle().value

            AppScaffold(
                topBar = {
                    AppTopBar(title = "Referral", onBack = onBackPress)
                },
            ) { paddingValues ->
                when (referralDataState) {
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
                        Toast.makeText(
                            context,
                            referralDataState.resId.toStringFromResId(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is UiState.ErrorRetry -> {
                        AppErrorScreen(
                            modifier = Modifier.padding(paddingValues),
                            text = referralDataState.resId.toStringFromResId(),
                        ) {
                            referralViewModel.getData()
                        }
                    }

                    is UiState.Success -> {
                        NewReferralDesign(
                            modifier = Modifier.padding(paddingValues),
                            referralData = referralDataState.data,
                            shareRefLink = shareReferralCode,
                        )
                    }

                    else -> {}
                }

            }
        }
    }
}
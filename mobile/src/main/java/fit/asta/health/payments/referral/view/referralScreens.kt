package fit.asta.health.payments.referral.view

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.main.Graph
import fit.asta.health.payments.referral.vm.ReferralViewModel

fun NavGraphBuilder.referralScreens(navController: NavHostController) {
    navigation(
        route = Graph.Referral.route,
        startDestination = ReferralScreen.Share.route
    ) {
        composable(ReferralScreen.Share.route) {
            val referralViewModel: ReferralViewModel = hiltViewModel()

            val checkCodeState = referralViewModel.applyCodeState.collectAsStateWithLifecycle()
            val state = referralViewModel.state.collectAsStateWithLifecycle()

            ShareReferralUi(
                referralDataState = state.value,
                applyCodeState = checkCodeState.value,
                onCheckRefereeData = referralViewModel::applyCode,
                onBackPress = navController::navigateUp,
                onTryAgain = referralViewModel::getData
            )
        }
    }
}
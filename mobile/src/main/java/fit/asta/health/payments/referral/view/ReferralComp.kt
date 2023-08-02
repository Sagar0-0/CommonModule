package fit.asta.health.payments.referral.view

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fit.asta.health.main.Graph
import fit.asta.health.payments.referral.vm.ReferralViewModel

fun NavGraphBuilder.referralComp(navController: NavHostController) {
    composable(Graph.Referral.route) {
        val referralViewModel: ReferralViewModel = hiltViewModel()

        val checkCodeState = referralViewModel.applyCodeState.collectAsStateWithLifecycle()
        val state = referralViewModel.state.collectAsStateWithLifecycle()


        ReferralScreen(
            referralDataState = state.value,
            applyCodeState = checkCodeState.value,
            onCheckRefereeData = referralViewModel::applyCode,
            onBackPress = navController::navigateUp,
            onTryAgain = referralViewModel::getData
        )
    }
}
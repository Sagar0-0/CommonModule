package fit.asta.health.referral

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.referral.view.ReferralDestination
import fit.asta.health.referral.view.ShareReferralUi
import fit.asta.health.referral.vm.ReferralViewModel

private const val REFERRAL_GRAPH_ROUTE = "graph_referral"

fun NavController.navigateToReferral(navOptions: NavOptions? = null) {
    this.navigate(REFERRAL_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.referralRoute(onBackPress: () -> Unit, shareReferralCode: (String) -> Unit) {
    navigation(
        route = REFERRAL_GRAPH_ROUTE,
        startDestination = ReferralDestination.Share.route
    ) {
        composable(ReferralDestination.Share.route) {
            val referralViewModel: ReferralViewModel = hiltViewModel()

            val state = referralViewModel.state.collectAsStateWithLifecycle()

            ShareReferralUi(
                referralDataState = state.value,
                shareReferralCode = shareReferralCode,
                onBackPress = onBackPress,
                onTryAgain = referralViewModel::getData
            )
        }
    }
}
package com.example.referral

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.referral.view.ReferralDestination
import com.example.referral.view.ShareReferralUi
import com.example.referral.vm.ReferralViewModel

private const val REFERRAL_GRAPH_ROUTE = "graph_referral"

fun NavController.navigateToReferral(navOptions: NavOptions? = null) {
    this.navigate(REFERRAL_GRAPH_ROUTE, navOptions)
}

fun NavGraphBuilder.referralRoute(navController: NavHostController) {
    navigation(
        route = REFERRAL_GRAPH_ROUTE,
        startDestination = ReferralDestination.Share.route
    ) {
        composable(ReferralDestination.Share.route) {
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
package fit.asta.health.splash

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fit.asta.health.R
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.main.Graph

fun NavGraphBuilder.splashScreen(navController: NavController, isConnected: Boolean) {
    composable(Graph.Splash.route) {
        val snackBarHostState = remember { SnackbarHostState() }
        val authViewModel: AuthViewModel = hiltViewModel()
        val context = LocalContext.current

        AppScaffold(
            snackBarHostState = snackBarHostState,
            content = { innerPadding ->
                OfflineSnackBar(
                    innerPaddingValues = innerPadding,
                    isConnected = isConnected,
                    snackBarHostState = snackBarHostState
                )
            })

        if (!PrefUtils.getOnboardingShownStatus(context)) {
            navController.navigate(Graph.Onboarding.route) {
                popUpToTop(navController)
            }
        } else {
            if (!authViewModel.isAuthenticated()) {
                navController.navigate(Graph.Authentication.route) {
                    popUpToTop(navController)
                }
            } else {
                navController.navigate(Graph.Home.route) {
                    popUpToTop(navController)
                }
            }

        }

    }
}

@Composable
private fun OfflineSnackBar(
    innerPaddingValues: PaddingValues,
    isConnected: Boolean,
    snackBarHostState: SnackbarHostState,
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
            .paint(
                painter = painterResource(id = R.drawable.splash_logo),
                alignment = Alignment.Center
            )
    ) {
        if (!isConnected) {
            val msg = stringResource(id = R.string.OFFLINE_STATUS)
            LaunchedEffect(Unit) {
                snackBarHostState.showSnackbar(
                    message = msg, duration = SnackbarDuration.Indefinite
                )
            }
        }
    }
}
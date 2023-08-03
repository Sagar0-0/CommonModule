@file:JvmName("AuthScreenKt")

package fit.asta.health.auth.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.main.Graph

fun NavGraphBuilder.authScreens(navController: NavHostController) {
    navigation(
        route = Graph.Authentication.route,
        startDestination = AuthScreen.SignIn.route
    ) {
        composable(AuthScreen.SignIn.route) {
            val context = LocalContext.current
            val authViewModel: AuthViewModel = hiltViewModel()
            SignInScreen(navController) {
                onSuccess(navController, context, authViewModel.isAuthenticated())
            }
        }
        composable(AuthScreen.Phone.route) {
            val context = LocalContext.current
            val authViewModel: AuthViewModel = hiltViewModel()
            PhoneLoginScreen {
                onSuccess(navController, context, authViewModel.isAuthenticated())
            }
        }
    }
}

private fun onSuccess(
    navController: NavHostController,
    context: Context,
    isAuthenticated: Boolean
) {
    if (isAuthenticated) {
        Toast.makeText(
            context, "Sign in Successful", Toast.LENGTH_SHORT
        ).show()
        navController.navigate(Graph.Home.route) {
            popUpToTop(navController)
        }
    }
}


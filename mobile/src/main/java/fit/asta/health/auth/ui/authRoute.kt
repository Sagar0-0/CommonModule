@file:JvmName("AuthScreenKt")

package fit.asta.health.auth.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fit.asta.health.auth.ui.screens.AuthDestination
import fit.asta.health.auth.ui.screens.PhoneLoginScreen
import fit.asta.health.auth.ui.screens.SignInScreen
import fit.asta.health.auth.ui.vm.AuthViewModel
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.main.Graph
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val AUTH_GRAPH_ROUTE = "graph_auth"
fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    if(navOptions==null) {
        this.navigate(AUTH_GRAPH_ROUTE){
            popUpToTop(this@navigateToAuth)
        }
    }else{
        this.navigate(AUTH_GRAPH_ROUTE,navOptions)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.authRoute(navController: NavHostController) {
    composable(AUTH_GRAPH_ROUTE){
        val context = LocalContext.current
        val authViewModel : AuthViewModel = hiltViewModel()
        val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
        val nestedNavController = rememberNavController()

        Box(contentAlignment = Alignment.Center) {
            NavHost(
                navController = nestedNavController,
                route = AUTH_GRAPH_ROUTE,
                startDestination = AuthDestination.SignIn.route
            ) {
                composable(AuthDestination.SignIn.route) {
                    SignInScreen(nestedNavController) {
                        authViewModel.signInWithGoogleCredentials(it)
                    }
                }
                composable(AuthDestination.Phone.route) {
                    PhoneLoginScreen{
                        authViewModel.signInWithGoogleCredentials(it)
                    }
                }
            }

            when(loginState){
                UiState.Loading->{
                    LoadingAnimation()
                }
                is UiState.Error->{
                    Text(text = (loginState as UiState.Error).resId.toStringFromResId())
                }
                is UiState.Success->{
                    LaunchedEffect(loginState){
                        Toast.makeText(
                            context, "Sign in Successful", Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(Graph.Home.route) {
                            popUpToTop(navController)
                        }
                    }
                }
                else->{}
            }
        }
    }
}


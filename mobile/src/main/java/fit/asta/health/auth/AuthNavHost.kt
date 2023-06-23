package fit.asta.health.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AuthNavHost(navHostController: NavHostController, onSuccess: () -> Unit) {
    NavHost(navController = navHostController, startDestination = "SignIn") {
        composable("SignIn") {
            SignInScreen(navHostController, onSuccess)
        }
        composable("Phone") {
            PhoneLoginScreen(onSuccess)
        }
    }
}
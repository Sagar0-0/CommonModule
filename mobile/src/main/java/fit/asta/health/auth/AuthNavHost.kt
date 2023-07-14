package fit.asta.health.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AuthNavHost(navHostController: NavHostController, onSuccess: () -> Unit) {
    NavHost(navController = navHostController, startDestination = AuthScreens.SignIn.route) {
        composable(AuthScreens.SignIn.route) {
            SignInScreen(navHostController, onSuccess)
        }
        composable(AuthScreens.Phone.route) {
            PhoneLoginScreen(onSuccess)
        }
    }
}


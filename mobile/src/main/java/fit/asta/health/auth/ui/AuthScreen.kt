package fit.asta.health.auth.ui

sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen("SignIn")
    object Phone : AuthScreen("Phone")

}
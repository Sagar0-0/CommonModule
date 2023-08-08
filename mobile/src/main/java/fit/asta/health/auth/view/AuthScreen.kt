package fit.asta.health.auth.view

sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen("SignIn")
    object Phone : AuthScreen("Phone")

}
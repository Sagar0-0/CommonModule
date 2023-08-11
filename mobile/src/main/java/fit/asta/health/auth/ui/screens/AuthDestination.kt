package fit.asta.health.auth.ui.screens

sealed class AuthDestination(val route: String) {
    object SignIn : AuthDestination("SignIn")
    object Phone : AuthDestination("Phone")
}
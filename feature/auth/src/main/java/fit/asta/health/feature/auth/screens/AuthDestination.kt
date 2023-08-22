package fit.asta.health.feature.auth.screens

sealed class AuthDestination(val route: String) {
    data object SignIn : AuthDestination("SignIn")
    data object Phone : AuthDestination("Phone")
}
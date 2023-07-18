package fit.asta.health.auth

sealed class AuthScreens(val route: String) {
    object SignIn : AuthScreens("SignIn")
    object Phone : AuthScreens("Phone")

}
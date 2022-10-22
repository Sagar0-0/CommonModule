package fit.asta.health.testimonials

sealed class TstScreen(val route: String) {
    object TstHome : TstScreen(route = "tst_home")
    object TstCreate : TstScreen(route = "tst_create")
}

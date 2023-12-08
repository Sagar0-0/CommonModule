package fit.asta.health.feature.testimonialsx.navigation


sealed class TestimonialNavRoutesX(val route: String) {
    data object Home : TestimonialNavRoutesX(route = "tst_home")
    data object Create : TestimonialNavRoutesX(route = "tst_create")
}
package fit.asta.health.feature.testimonials.navigation


sealed class TestimonialNavRoutes(val route: String) {
    data object Home : TestimonialNavRoutes(route = "tst_home")
    data object Create : TestimonialNavRoutes(route = "tst_create")
}
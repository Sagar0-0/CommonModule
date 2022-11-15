package fit.asta.health.testimonials.view

sealed class TestimonialsRoute(val route: String) {
    object Home : TestimonialsRoute(route = "tst_home")
    object Create : TestimonialsRoute(route = "tst_create")
}

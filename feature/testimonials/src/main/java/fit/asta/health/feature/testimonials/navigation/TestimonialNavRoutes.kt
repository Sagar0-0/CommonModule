package fit.asta.health.feature.testimonials.navigation

sealed class TestimonialNavRoutes(val route: String) {
    data object Home : TestimonialNavRoutes(route = "tst_home")
    data object Create : TestimonialNavRoutes(route = "tst_create")
    data object BeforeImgCropper : TestimonialNavRoutes(route = "before_img_cropper")
    data object AfterImgCropper : TestimonialNavRoutes(route = "after_img_cropper")
}
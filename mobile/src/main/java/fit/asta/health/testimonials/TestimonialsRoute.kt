package fit.asta.health.testimonials

sealed class TestimonialsRoute(val route: String) {
    object Home : TestimonialsRoute(route = "tst_home")
    object Create : TestimonialsRoute(route = "tst_create")
    object BeforeImgCropper : TestimonialsRoute(route = "before_img_cropper")
    object AfterImgCropper : TestimonialsRoute(route = "after_img_cropper")
}

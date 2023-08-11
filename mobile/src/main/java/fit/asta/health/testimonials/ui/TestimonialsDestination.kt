package fit.asta.health.testimonials.ui

sealed class TestimonialsDestination(val route: String) {
    object Home : TestimonialsDestination(route = "tst_home")
    object Create : TestimonialsDestination(route = "tst_create")
    object BeforeImgCropper : TestimonialsDestination(route = "before_img_cropper")
    object AfterImgCropper : TestimonialsDestination(route = "after_img_cropper")
}

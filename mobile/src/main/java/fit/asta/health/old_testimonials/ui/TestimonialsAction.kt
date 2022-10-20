package fit.asta.health.old_testimonials.ui

import fit.asta.health.old_testimonials.data.TestimonialData

sealed class TestimonialsAction {

    class LoadTestimonial(val testimonial: TestimonialData) : TestimonialsAction()
    class LoadTestimonials(val list: List<TestimonialData>) : TestimonialsAction()
    object Empty : TestimonialsAction()
    class Error(val message: String) : TestimonialsAction()
}
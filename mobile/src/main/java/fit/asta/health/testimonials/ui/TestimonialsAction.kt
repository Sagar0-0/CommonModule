package fit.asta.health.testimonials.ui

import fit.asta.health.testimonials.data.TestimonialData

sealed class TestimonialsAction {

    class LoadTestimonial(val testimonial: TestimonialData) : TestimonialsAction()
    class LoadTestimonials(val list: List<TestimonialData>) : TestimonialsAction()
    object Empty : TestimonialsAction()
    class Error(val message: String) : TestimonialsAction()
}
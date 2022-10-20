package fit.asta.health.old_testimonials.viewmodel

import fit.asta.health.old_testimonials.data.TestimonialData

interface TestimonialsDataStore {
    fun createMyTestimonial(userId: String, testimonial: TestimonialData)
    fun updateMyTestimonial(testimonial: TestimonialData): TestimonialData
    fun updateTestimonialList(list: List<TestimonialData>)
    fun getTestimonialByPos(position: Int): TestimonialData
}
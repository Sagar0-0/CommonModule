package fit.asta.health.testimonials.viewmodel

import fit.asta.health.testimonials.data.TestimonialData

interface TestimonialsDataStore {
    fun createMyTestimonial(userId: String, testimonial: TestimonialData)
    fun updateMyTestimonial(testimonial: TestimonialData): TestimonialData
    fun updateTestimonialList(list: List<TestimonialData>)
    fun getTestimonialByPos(position: Int): TestimonialData
}
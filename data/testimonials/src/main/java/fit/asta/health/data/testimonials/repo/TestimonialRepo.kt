package fit.asta.health.data.testimonials.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.testimonials.model.CreateTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial

interface TestimonialRepo {
    suspend fun getTestimonials(index: Int, limit: Int): ResponseState<List<Testimonial>>
    suspend fun getTestimonial(userId: String): ResponseState<Testimonial>
    suspend fun updateTestimonial(testimonial: Testimonial): ResponseState<CreateTestimonialResponse>
}
package fit.asta.health.data.testimonials.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.testimonials.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial

interface TestimonialRepo {
    suspend fun getAllTestimonials(index: Int, limit: Int): ResponseState<List<Testimonial>>
    suspend fun getUserTestimonial(userId: String): ResponseState<Testimonial>
    suspend fun saveUserTestimonial(testimonial: Testimonial): ResponseState<SaveTestimonialResponse>
}
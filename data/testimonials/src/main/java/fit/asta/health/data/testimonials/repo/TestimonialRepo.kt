package fit.asta.health.data.testimonials.repo

import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.network.data.Status
import kotlinx.coroutines.flow.Flow

interface TestimonialRepo {
    suspend fun getTestimonials(index: Int, limit: Int): ApiResponse<List<Testimonial>>
    suspend fun getTestimonial(userId: String): ApiResponse<Testimonial>
    suspend fun updateTestimonial(testimonial: Testimonial): Flow<Status>
}
package fit.asta.health.testimonials.data.repo

import fit.asta.health.network.data.ApiResponse
import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.data.model.Testimonial
import kotlinx.coroutines.flow.Flow


interface TestimonialRepo {
    suspend fun getTestimonials(index: Int, limit: Int): ApiResponse<List<Testimonial>>
    suspend fun getTestimonial(userId: String): ApiResponse<Testimonial>
    suspend fun updateTestimonial(testimonial: Testimonial): Flow<Status>
}
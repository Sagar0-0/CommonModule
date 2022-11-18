package fit.asta.health.testimonials.model

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.domain.Testimonial
import kotlinx.coroutines.flow.Flow


interface TestimonialRepo {
    suspend fun getTestimonials(index: Int, limit: Int): List<Testimonial>
    suspend fun getTestimonial(userId: String): Flow<Testimonial>
    suspend fun updateTestimonial(testimonial: Testimonial): Flow<Status>
}
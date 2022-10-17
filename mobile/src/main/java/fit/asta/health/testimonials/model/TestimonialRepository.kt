package fit.asta.health.testimonials.model

import fit.asta.health.testimonials.model.domain.Testimonial
import kotlinx.coroutines.flow.Flow


interface TestimonialRepository {
    suspend fun getTestimonials(limit: Int, index: Int): Flow<Testimonial>
}
package fit.asta.health.testimonials

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.data.TestimonialData
import kotlinx.coroutines.flow.Flow

interface TestimonialsRepo {
    suspend fun createTestimonial(testimonial: TestimonialData?): Flow<Status>
    suspend fun fetchTestimonial(userId: String): Flow<TestimonialData>
    suspend fun updateTestimonial(testimonial: TestimonialData?): Flow<Status>
    suspend fun fetchTestimonialsList(limit: Int, index: Int): Flow<List<TestimonialData>>
}
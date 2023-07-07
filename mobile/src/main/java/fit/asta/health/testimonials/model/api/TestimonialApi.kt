package fit.asta.health.testimonials.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.model.domain.TestimonialRes
import fit.asta.health.testimonials.model.domain.TestimonialsRes
import okhttp3.MultipartBody

//Testimonial Endpoints
interface TestimonialApi {
    suspend fun getTestimonials(index: Int, limit: Int): TestimonialsRes
    suspend fun createTestimonial(tml: Testimonial, files: List<MultipartBody.Part>): Status
    suspend fun getUserTestimonial(userId: String): TestimonialRes
}
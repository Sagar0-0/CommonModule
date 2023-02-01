package fit.asta.health.testimonials.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import okhttp3.MultipartBody

//Testimonial Endpoints
interface TestimonialApi {
    suspend fun getTestimonials(index: Int, limit: Int): NetTestimonialsRes
    suspend fun createTestimonial(tml: NetTestimonial, files: List<MultipartBody.Part>): Status
    suspend fun getUserTestimonial(userId: String): NetTestimonialRes
}
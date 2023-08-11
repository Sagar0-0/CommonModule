package fit.asta.health.testimonials.data.remote

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.data.model.Testimonial
import fit.asta.health.testimonials.data.model.TestimonialRes
import fit.asta.health.testimonials.data.model.TestimonialsRes
import okhttp3.MultipartBody
import retrofit2.http.*

//Testimonial Endpoints
interface TestimonialApiService {

    @GET("testimonial/list/get?")
    suspend fun getTestimonials(
        @Query("index") index: Int,
        @Query("limit") limit: Int,
    ): TestimonialsRes

    @PUT("testimonial/put/")
    @Multipart
    suspend fun createTestimonial(
        @Part("json") netTestimonial: Testimonial,
        @Part files: List<MultipartBody.Part>,
    ): Status

    @GET("testimonial/get/?")
    suspend fun getUserTestimonial(@Query("uid") userId: String): TestimonialRes
}

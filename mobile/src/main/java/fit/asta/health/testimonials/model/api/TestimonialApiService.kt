package fit.asta.health.testimonials.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import okhttp3.MultipartBody
import retrofit2.http.*

//Testimonial Endpoints
interface TestimonialApiService {

    @GET("testimonial/list/get?")
    suspend fun getTestimonials(
        @Query("index") index: Int,
        @Query("limit") limit: Int
    ): NetTestimonialsRes

    @PUT("testimonial/put/")
    @Multipart
    suspend fun createTestimonial(
        @Part("json") netTestimonial: NetTestimonial,
        @Part files: List<MultipartBody.Part>
    ): Status

    @GET("testimonial/get/?")
    suspend fun getUserTestimonial(@Query("uid") userId: String): NetTestimonialRes
}

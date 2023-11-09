package fit.asta.health.data.testimonials.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.testimonials.model.CreateTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial
import okhttp3.MultipartBody
import retrofit2.http.*

//Testimonial Endpoints
interface TestimonialApiService {

    @GET("testimonial/list/get?")
    suspend fun getTestimonials(
        @Query("index") index: Int,
        @Query("limit") limit: Int,
    ): Response<List<Testimonial>>

    @PUT("testimonial/put/")
    @Multipart
    suspend fun createTestimonial(
        @Part("json") netTestimonial: Testimonial,
        @Part files: List<MultipartBody.Part>,
    ): Response<CreateTestimonialResponse>

    @GET("testimonial/get/?")
    suspend fun getUserTestimonial(@Query("uid") userId: String): Response<Testimonial>
}

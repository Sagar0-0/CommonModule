package fit.asta.health.data.testimonials.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.testimonials.remote.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.remote.model.Testimonial
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

//Testimonial Endpoints
interface TestimonialApi {

    @GET("testimonial/list/get?")
    suspend fun getAllTestimonials(
        @Query("index") index: Int,
        @Query("limit") limit: Int,
    ): Response<List<Testimonial>>

    @PUT("testimonial/put/")
    @Multipart
    suspend fun createTestimonial(
        @Part("json") netTestimonial: Testimonial,
        @Part files: List<MultipartBody.Part>,
    ): Response<SaveTestimonialResponse>

    @GET("testimonial/get/?")
    suspend fun getUserTestimonial(@Query("uid") userId: String): Response<Testimonial>
}

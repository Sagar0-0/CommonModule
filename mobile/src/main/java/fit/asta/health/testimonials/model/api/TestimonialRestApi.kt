package fit.asta.health.testimonials.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.model.domain.TestimonialRes
import fit.asta.health.testimonials.model.domain.TestimonialsRes
import okhttp3.MultipartBody
import okhttp3.OkHttpClient


//Testimonial Endpoints
class TestimonialRestApi(baseUrl: String, client: OkHttpClient) : TestimonialApi {

    private val apiService: TestimonialApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(TestimonialApiService::class.java)

    override suspend fun getTestimonials(index: Int, limit: Int): TestimonialsRes {
        return apiService.getTestimonials(index, limit)
    }

    override suspend fun createTestimonial(
        tml: Testimonial,
        files: List<MultipartBody.Part>
    ): Status {
        return apiService.createTestimonial(netTestimonial = tml, files = files)
    }

    override suspend fun getUserTestimonial(userId: String): TestimonialRes {
        return apiService.getUserTestimonial(userId)
    }

}
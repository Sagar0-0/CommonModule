package fit.asta.health.testimonials.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import fit.asta.health.utils.NetworkUtil
import okhttp3.MultipartBody
import okhttp3.OkHttpClient


//Testimonial Endpoints
class TestimonialRestApi(baseUrl: String, client: OkHttpClient) :
    TestimonialApi {

    private val apiService: TestimonialApiService = NetworkUtil
        .getRetrofit(baseUrl = baseUrl, client = client)
        .create(TestimonialApiService::class.java)

    override suspend fun getTestimonials(index: Int, limit: Int): NetTestimonialsRes {
        return apiService.getTestimonials(index, limit)
    }

    override suspend fun createTestimonial(
        tml: NetTestimonial,
        files: List<MultipartBody.Part>
    ): Status {
        return apiService.createTestimonial(netTestimonial = tml, files = files)
    }

    override suspend fun getUserTestimonial(userId: String): NetTestimonialRes {
        return apiService.getUserTestimonial(userId)
    }
}
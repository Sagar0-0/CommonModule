package fit.asta.health.data.testimonials.repo

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.testimonials.model.CreateTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.remote.TestimonialApiService
import fit.asta.health.network.utils.InputStreamRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class TestimonialRepoImpl
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteApi: TestimonialApiService,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : TestimonialRepo {

    override suspend fun getTestimonials(index: Int, limit: Int): ResponseState<List<Testimonial>> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.getTestimonials(index = index, limit = limit)
            }
        }
    }

    override suspend fun getTestimonial(userId: String): ResponseState<Testimonial> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.getUserTestimonial(userId)
            }
        }
    }

    override suspend fun updateTestimonial(testimonial: Testimonial): ResponseState<CreateTestimonialResponse> {
        val parts: ArrayList<MultipartBody.Part> = ArrayList()
        testimonial.media.forEach {
            if (it.localUrl != null) {
                //val body = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                parts.add(
                    MultipartBody.Part.createFormData(
                        name = "file",
                        filename = it.name,
                        body = InputStreamRequestBody(context.contentResolver, it.localUrl!!)
                    )
                )
            }
        }

        return withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.createTestimonial(testimonial, parts)
            }
        }
    }
}
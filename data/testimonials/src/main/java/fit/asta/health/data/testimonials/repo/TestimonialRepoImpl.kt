package fit.asta.health.data.testimonials.repo

import android.content.ContentResolver
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.testimonials.remote.TestimonialApi
import fit.asta.health.data.testimonials.remote.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.remote.model.Testimonial
import fit.asta.health.data.testimonials.util.TestimonialApiHandler
import fit.asta.health.network.utils.InputStreamRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class TestimonialRepoImpl
@Inject constructor(
    private val contentResolver: ContentResolver,
    private val remoteApi: TestimonialApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TestimonialRepo {

    override suspend fun getAllTestimonials(
        index: Int,
        limit: Int
    ): ResponseState<List<Testimonial>> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.getAllTestimonials(index = index, limit = limit)
            }
        }
    }

    override suspend fun getUserTestimonial(userId: String): ResponseState<Testimonial> {
        return withContext(coroutineDispatcher) {
            getApiResponseState(errorHandler = TestimonialApiHandler()) {
                remoteApi.getUserTestimonial(userId)
            }
        }
    }

    override suspend fun saveUserTestimonial(testimonial: Testimonial):
        ResponseState<SaveTestimonialResponse> {

        val parts: ArrayList<MultipartBody.Part> = ArrayList()

        listOf(
            testimonial.afterImage,
            testimonial.beforeImage,
            testimonial.videoMedia
        ).forEach {
            if (it != null) {
                parts.add(
                    MultipartBody.Part.createFormData(
                        name = "file",
                        filename = it.name,
                        body = InputStreamRequestBody(contentResolver, it.localUrl!!)
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
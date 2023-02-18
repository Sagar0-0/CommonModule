package fit.asta.health.testimonials.model

import android.content.Context
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.api.TestimonialApi
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.utils.InputStreamRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException


class TestimonialRepoImpl(
    private val context: Context,
    private val remoteApi: TestimonialApi,
    private val mapper: TestimonialDataMapper,
) : TestimonialRepo {

    override suspend fun getTestimonials(index: Int, limit: Int): ApiResponse<List<Testimonial>> {

        return try {
            val response = remoteApi.getTestimonials(index = index, limit = limit)
            ApiResponse.Success(data = mapper.mapToDomainModel(response.testimonials))
        } catch (e: HttpException) {
            ApiResponse.HttpError(code = e.code(), msg = e.message(), ex = e)
        } catch (e: IOException) {
            ApiResponse.Error(exception = e)
        }
    }

    override suspend fun getTestimonial(userId: String): ApiResponse<Testimonial> {
        return try {
            val response = remoteApi.getUserTestimonial(userId)
            ApiResponse.Success(data = mapper.mapToDomainModel(response.testimonial))
        } catch (e: HttpException) {
            ApiResponse.HttpError(code = e.code(), msg = e.message(), ex = e)
        } catch (e: IOException) {
            ApiResponse.Error(exception = e)
        }
    }

    override suspend fun updateTestimonial(testimonial: Testimonial): Flow<Status> {

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

        return flow {
            emit(remoteApi.createTestimonial(mapper.mapToNetworkModel(testimonial), parts))
        }
    }
}
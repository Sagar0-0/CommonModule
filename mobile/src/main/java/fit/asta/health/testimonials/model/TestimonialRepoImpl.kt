package fit.asta.health.testimonials.model

import android.content.Context
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.utils.InputStreamRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody


class TestimonialRepoImpl(
    private val context: Context,
    private val remoteApi: RemoteApis,
    private val mapper: TestimonialDataMapper,
) : TestimonialRepo {

    override suspend fun getTestimonials(index: Int, limit: Int): List<Testimonial> {
        return mapper.mapToDomainModel(
            remoteApi.getTestimonials(
                index = index,
                limit = limit
            ).testimonials
        )
    }

    override suspend fun getTestimonial(userId: String): Flow<Testimonial> {
        return flow {
            emit(
                mapper.mapToDomainModel(remoteApi.getUserTestimonial(userId).testimonial)
            )
        }
    }

    override suspend fun updateTestimonial(testimonial: Testimonial): Flow<Status> {

        val parts: ArrayList<MultipartBody.Part> = ArrayList()
        testimonial.media.forEach {
            if (it.localUrl != null) {
                //val contentPart = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                val contentPart = InputStreamRequestBody(context.contentResolver, it.localUrl!!)
                parts.add(
                    MultipartBody.Part.createFormData(
                        name = "file",
                        filename = it.name,
                        body = contentPart
                    )
                )
            }
        }

        return flow {
            withContext(Dispatchers.IO) {
                emit(remoteApi.updateTestimonial(mapper.mapToNetworkModel(testimonial), parts))
            }
        }
    }
}
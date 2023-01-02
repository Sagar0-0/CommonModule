package fit.asta.health.testimonials.model

import android.content.Context
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.FileInfo
import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.utils.InputStreamRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun updateTestimonial(testimonial: Testimonial, fileInfo: List<FileInfo>):
            Flow<Status> {

        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)

        fileInfo.forEach {

            val contentPart = InputStreamRequestBody(context.contentResolver, it.file)
            multipart.addFormDataPart(name = "file", filename = it.name, contentPart)
        }

        return flow {
            emit(
                remoteApi.updateTestimonial(
                    mapper.mapToNetworkModel(testimonial),
                    multipart.build()
                )
            )
        }
    }
}
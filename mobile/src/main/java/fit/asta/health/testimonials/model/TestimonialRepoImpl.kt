package fit.asta.health.testimonials.model

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.domain.Testimonial
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestimonialRepoImpl(
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
        return flow {
            emit(
                remoteApi.updateTestimonial(mapper.mapToNetworkModel(testimonial))
            )
        }
    }
}
package fit.asta.health.testimonials.model

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.testimonials.model.domain.Testimonial
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestimonialRepoImpl(
    private val remoteApi: RemoteApis,
    private val mapper: TestimonialDataMapper,
) : TestimonialRepo {

    override suspend fun getTestimonials(limit: Int, index: Int): Flow<Testimonial> {
        return flow {
            emit(
                mapper.mapToDomainModel(
                    remoteApi.getTestimonials(
                        limit = limit,
                        index = index
                    )
                )
            )
        }
    }
}
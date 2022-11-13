package fit.asta.health.testimonials.model

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestimonialRepoImpl(
    private val remoteApi: RemoteApis,
    private val mapper: TestimonialDataMapper,
) : TestimonialRepo {

    override suspend fun getTestimonials(limit: Int, index: Int): Flow<NetTestimonialsRes> {
        return flow {
            emit(
                remoteApi.getTestimonials(limit = limit, index = index)
            )
        }
    }

    override suspend fun getTestimonial(userId: String): Flow<NetTestimonialRes> {
        return flow {
            emit(
                remoteApi.getUserTestimonial(userId)
            )
        }
    }

    override suspend fun updateTestimonial(netTestimonial: NetTestimonial): Flow<Status> {
        return flow {
            emit(
                remoteApi.updateTestimonial(netTestimonial)
            )
        }
    }
}
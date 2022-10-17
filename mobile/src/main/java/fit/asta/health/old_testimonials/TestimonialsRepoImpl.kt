package fit.asta.health.old_testimonials

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.Status
import fit.asta.health.old_testimonials.data.TestimonialData
import fit.asta.health.old_testimonials.data.TestimonialDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestimonialsRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: TestimonialDataMapper

) : TestimonialsRepo {

    override suspend fun createTestimonial(testimonial: TestimonialData?): Flow<Status> {
        return flow {
            testimonial?.let {
                val data = dataMapper.toNetTestimonial(testimonial)
                remoteApi.postTestimonial(data)
            }
        }
    }

    override suspend fun fetchTestimonial(userId: String): Flow<TestimonialData> {
        return flow {
            emit(dataMapper.toTestimonial(remoteApi.getTestimonial(userId).data))
        }
    }

    override suspend fun updateTestimonial(testimonial: TestimonialData?): Flow<Status> {
        return flow {
            testimonial?.let {
                val data = dataMapper.toNetTestimonial(testimonial)
                remoteApi.putTestimonial(data)
            }
        }
    }

    override suspend fun fetchTestimonialsList(
        limit: Int,
        index: Int
    ): Flow<List<TestimonialData>> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getTestimonialList(limit, index)))
        }
    }
}
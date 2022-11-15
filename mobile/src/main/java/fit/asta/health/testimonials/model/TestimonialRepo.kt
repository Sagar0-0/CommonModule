package fit.asta.health.testimonials.model

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import fit.asta.health.testimonials.model.network.NetTestimonialsRes
import kotlinx.coroutines.flow.Flow


interface TestimonialRepo {

    suspend fun getTestimonials(page: Int, limit: Int): Flow<NetTestimonialsRes>
    suspend fun getTestimonial(userId: String): Flow<NetTestimonialRes>
    suspend fun updateTestimonial(netTestimonial: NetTestimonial): Flow<Status>

}


package fit.asta.health.testimonials.model

import fit.asta.health.network.data.Status
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialRes
import kotlinx.coroutines.flow.Flow


interface TestimonialRepo {
    suspend fun getTestimonials(limit: Int, index: Int): Flow<List<Testimonial>>
    suspend fun getTestimonial(userId: String): Flow<NetTestimonialRes>
    suspend fun updateTestimonial(netTestimonial: NetTestimonial): Flow<Status>
}
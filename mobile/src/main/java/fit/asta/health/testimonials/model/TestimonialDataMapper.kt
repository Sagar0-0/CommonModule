package fit.asta.health.testimonials.model

import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.model.network.response.NetTestimonialRes

class TestimonialDataMapper {

    fun mapToDomainModel(networkModel: NetTestimonialRes): Testimonial {
        return Testimonial(
        )
    }

    fun mapToNetworkModel(domainModel: Testimonial): NetTestimonialRes {
        TODO("Not yet implemented")
    }
}
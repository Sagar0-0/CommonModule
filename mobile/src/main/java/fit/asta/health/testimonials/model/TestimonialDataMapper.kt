package fit.asta.health.testimonials.model

import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialsRes

class TestimonialDataMapper {

    fun mapToDomainModel(networkModel: NetTestimonialsRes): List<Testimonial> {
        TODO("Not yet implemented")
    }

    fun mapToNetworkModel(domainModel: Testimonial): NetTestimonial {
        TODO("Not yet implemented")
    }

}
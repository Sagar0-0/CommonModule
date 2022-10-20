package fit.asta.health.testimonials.model

import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.model.network.response.NetTestimonialRes
import fit.asta.health.utils.DomainMapper

class TestimonialDataMapper : DomainMapper<NetTestimonialRes, Testimonial> {

    override fun mapToDomainModel(networkModel: NetTestimonialRes): Testimonial {
        return Testimonial(
        )
    }

    override fun mapFromDomainModel(domainModel: Testimonial): NetTestimonialRes {
        TODO("Not yet implemented")
    }
}
package fit.asta.health.testimonials.data

import fit.asta.health.testimonials.networkdata.TestimonialListResponse
import fit.asta.health.testimonials.networkdata.TestimonialNetData

class TestimonialDataMapper {

    fun toMap(testimonials: TestimonialListResponse): List<TestimonialData> {
        return testimonials.data.map {
            toTestimonial(it)
        }
    }

    fun toTestimonial(netTestimonial: TestimonialNetData): TestimonialData {
        return TestimonialData().apply {
            uid = netTestimonial.uid
            imageURL = netTestimonial.url
            name = netTestimonial.name
            testimonial = netTestimonial.tml
            designation = netTestimonial.des
            organization = netTestimonial.org
        }
    }

    fun toNetTestimonial(testimonial: TestimonialData): TestimonialNetData {
        return TestimonialNetData().apply {
            uid = testimonial.uid
            userId = testimonial.userId
            url = testimonial.imageURL
            tml = testimonial.testimonial
            name = testimonial.name
            des = testimonial.designation
            org = testimonial.organization
        }
    }
}
package fit.asta.health.testimonials.viewmodel

import fit.asta.health.testimonials.data.TestimonialData

class TestimonialsDataStoreImpl : TestimonialsDataStore {
    private var listTestimonials: List<TestimonialData> = listOf()
    private var userTestimonial = TestimonialData()

    override fun createMyTestimonial(userId: String, testimonial: TestimonialData) {
        userTestimonial = testimonial
        userTestimonial.userId = userId
    }

    override fun updateMyTestimonial(testimonial: TestimonialData): TestimonialData {
        userTestimonial.testimonial = testimonial.testimonial
        userTestimonial.designation = testimonial.designation
        userTestimonial.organization = testimonial.organization
        return userTestimonial
    }

    override fun updateTestimonialList(list: List<TestimonialData>) {
        listTestimonials = list
    }

    override fun getTestimonialByPos(position: Int): TestimonialData {
        return listTestimonials[position]
    }
}
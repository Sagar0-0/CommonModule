package fit.asta.health.testimonials.ui

import fit.asta.health.testimonials.data.TestimonialData
import fit.asta.health.testimonials.viewmodel.TestimonialsViewModel

class ClickListenerImpl(val viewModel: TestimonialsViewModel) : ClickListener {

    override fun onClickFab() {
        viewModel.fetchTestimonial()
    }

    override fun onClickSubmit(testimonial: TestimonialData) {
        viewModel.submitTestimonial(testimonial)
    }
}

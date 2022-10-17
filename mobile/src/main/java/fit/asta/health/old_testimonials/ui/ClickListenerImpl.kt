package fit.asta.health.old_testimonials.ui

import fit.asta.health.old_testimonials.data.TestimonialData
import fit.asta.health.old_testimonials.viewmodel.TestimonialsViewModel

class ClickListenerImpl(val viewModel: TestimonialsViewModel) : ClickListener {

    override fun onClickFab() {
        viewModel.fetchTestimonial()
    }

    override fun onClickSubmit(testimonial: TestimonialData) {
        viewModel.submitTestimonial(testimonial)
    }
}

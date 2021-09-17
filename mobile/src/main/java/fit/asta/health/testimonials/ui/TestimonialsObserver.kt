package fit.asta.health.testimonials.ui

import androidx.lifecycle.Observer

class TestimonialsObserver(private val testimonialsView: TestimonialsView) :
    Observer<TestimonialsAction> {
    override fun onChanged(action: TestimonialsAction) {
        when (action) {
            is TestimonialsAction.LoadTestimonial -> {
                val state = TestimonialsView.State.LoadTestimonial(action.testimonial)
                testimonialsView.changeState(state)
            }
            is TestimonialsAction.LoadTestimonials -> {
                val state = TestimonialsView.State.LoadTestimonials(action.list)
                testimonialsView.changeState(state)
            }
            is TestimonialsAction.Empty -> {
                val state = TestimonialsView.State.Empty
                testimonialsView.changeState(state)
            }
            is TestimonialsAction.Error -> {
                val state = TestimonialsView.State.Error(action.message)
                testimonialsView.changeState(state)
            }
        }
    }
}
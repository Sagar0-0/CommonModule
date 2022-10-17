package fit.asta.health.old_testimonials.ui

import android.app.Activity
import android.view.View
import fit.asta.health.old_testimonials.data.TestimonialData

interface TestimonialsView {

    fun setContentView(activity: Activity): View?
    fun changeState(state: State)

    /*fun onOptionsItemSelected(item: MenuItem, onLoadTestimonial: () -> Unit, callBack: () -> Boolean): Boolean*/
    fun fabClickListener(listener: ClickListener)
    fun submitClickListener(listener: ClickListener)

    sealed class State {
        class LoadTestimonial(val testimonial: TestimonialData) : State()
        class LoadTestimonials(val list: List<TestimonialData>) : State()
        object Empty : State()
        class Error(val message: String) : State()
    }
}
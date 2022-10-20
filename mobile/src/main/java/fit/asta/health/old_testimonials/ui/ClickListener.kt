package fit.asta.health.old_testimonials.ui

import fit.asta.health.old_testimonials.data.TestimonialData

interface ClickListener {
    fun onClickFab()
    fun onClickSubmit(testimonial: TestimonialData)
}

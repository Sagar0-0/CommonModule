package fit.asta.health.testimonials.ui

import fit.asta.health.testimonials.data.TestimonialData

interface ClickListener {
    fun onClickFab()
    fun onClickSubmit(testimonial: TestimonialData)
}

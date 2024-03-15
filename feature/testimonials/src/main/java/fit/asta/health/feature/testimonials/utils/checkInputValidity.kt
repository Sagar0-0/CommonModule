package fit.asta.health.feature.testimonials.utils

import fit.asta.health.data.testimonials.remote.model.Testimonial
import fit.asta.health.data.testimonials.remote.model.TestimonialType


/**
 * This Function checks if the inputs are valid so that the Submit Button can be Enabled
 *
 * @param testimonial This contains the user's Testimonials which we need to check for validity
 */
fun checkInputValidity(testimonial: Testimonial): Boolean {

    val mediaValidity = when (TestimonialType.from(testimonial.type)) {
        TestimonialType.TEXT -> true

        TestimonialType.IMAGE -> {
            testimonial.beforeImage != null && testimonial.afterImage != null
        }

        TestimonialType.VIDEO -> {
            testimonial.videoMedia != null
        }
    }

    val titleValidity = (testimonial.title.length in 4..32)
    val testimonialValidity = testimonial.testimonial.length in 4..256
    val orgValidity = testimonial.user.org.length in 4..32
    val roleValidity = testimonial.user.role.length in 4..32

    return mediaValidity && titleValidity && testimonialValidity && orgValidity && roleValidity
}
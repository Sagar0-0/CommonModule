package fit.asta.health.feature.testimonials.utils

import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.model.TestimonialType


/**
 * This Function checks if the inputs are valid so that the Submit Button can be Enabled
 *
 * @param testimonial This contains the user's Testimonials which we need to check for validity
 */
fun checkInputValidity(testimonial: Testimonial): Boolean {

    val mediaValidity = when (TestimonialType.from(testimonial.type)) {
        TestimonialType.TEXT -> true

        TestimonialType.IMAGE -> {
            val beforeData = testimonial.media[0]
            val afterData = testimonial.media[1]
            ((beforeData.localUrl != null || beforeData.url.isNotBlank())
                    && (afterData.localUrl != null || afterData.url.isNotBlank()))
        }

        TestimonialType.VIDEO -> {
            testimonial.media[0].localUrl != null
                    || testimonial.media[0].url.isNotBlank()
        }
    }

    val titleValidity = (testimonial.title.length in 4..32)
    val testimonialValidity = testimonial.testimonial.length in 4..256
    val orgValidity = testimonial.user.org.length in 4..32
    val roleValidity = testimonial.user.role.length in 4..32

    return mediaValidity && titleValidity && testimonialValidity && orgValidity && roleValidity
}
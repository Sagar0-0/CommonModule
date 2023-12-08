package fit.asta.health.data.testimonials.util

import fit.asta.health.common.utils.ApiErrorHandler
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.data.testimonials.model.Testimonial


class TestimonialApiHandler : ApiErrorHandler() {

    override fun <T> fetchStatusCodeMessage(status: Response.Status): ResponseState<T> {
        val result = super.fetchStatusCodeMessage<T>(status)

        return if (status.code == 4)
            ResponseState.Success(
                data = Testimonial(
                    media = listOf(Media(), Media())
                )
            ) as ResponseState<T>
        else
            result
    }
}
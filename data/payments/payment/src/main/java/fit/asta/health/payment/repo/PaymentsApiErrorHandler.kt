package fit.asta.health.payment.repo

import fit.asta.health.common.utils.ApiErrorHandler
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.resources.strings.R
import javax.inject.Inject

class PaymentsApiErrorHandler
@Inject constructor() : ApiErrorHandler() {
    override fun <T> fetchStatusCodeMessage(status: Response.Status): ResponseState<T> {
        return when (status.code) {
            8 -> {
                ResponseState.ErrorMessage(R.string.razorpay_error)
            }

            9 -> {
                ResponseState.ErrorMessage(R.string.refund_exceeded_error)
            }

            else -> {
                super.fetchStatusCodeMessage(status)
            }
        }
    }

}
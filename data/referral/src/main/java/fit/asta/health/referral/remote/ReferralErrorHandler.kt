package fit.asta.health.referral.remote

import fit.asta.health.common.utils.ApiErrorHandler
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.resources.strings.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReferralErrorHandler @Inject constructor() : ApiErrorHandler() {
    override fun <T> fetchStatusMessage(code: Int): ResponseState<T> {
        return ResponseState.ErrorMessage(
            when (code) {
                101 -> {
                    R.string.refer_code_not_exist
                }

                102 -> {
                    R.string.own_refer_code_error
                }

                103 -> {
                    R.string.already_referred_error
            }

            104 -> {
                R.string.referring_my_referred
            }

            else -> {
                R.string.unknown_error
            }
        }
        )
    }
}
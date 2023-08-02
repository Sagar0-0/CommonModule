package fit.asta.health.payments.referral.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.referral.model.ApplyCodeResponse
import fit.asta.health.payments.referral.model.ReferralDataResponse

interface ReferralRepo {
    suspend fun getData(uid: String): ResponseState<ReferralDataResponse>
    suspend fun applyCode(refCode: String, uid: String): ResponseState<ApplyCodeResponse>

}

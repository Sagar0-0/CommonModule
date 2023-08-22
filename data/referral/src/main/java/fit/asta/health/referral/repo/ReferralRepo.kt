package fit.asta.health.referral.repo

import fit.asta.health.referral.model.ApplyCodeResponse
import fit.asta.health.referral.model.ReferralDataResponse
import fit.asta.health.common.utils.ResponseState

interface ReferralRepo {
    suspend fun getData(uid: String): ResponseState<ReferralDataResponse>
    suspend fun applyCode(refCode: String, uid: String): ResponseState<ApplyCodeResponse>

}

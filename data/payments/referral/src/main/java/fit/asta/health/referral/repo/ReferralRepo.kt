package fit.asta.health.referral.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.referral.remote.model.ReferralDataResponse

interface ReferralRepo {
    suspend fun getData(uid: String): ResponseState<ReferralDataResponse>

}

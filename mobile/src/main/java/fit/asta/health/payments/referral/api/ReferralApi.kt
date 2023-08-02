package fit.asta.health.payments.referral.api

import fit.asta.health.payments.referral.model.ApplyCodeResponse
import fit.asta.health.payments.referral.model.ReferralDataResponse

interface ReferralApi {

    suspend fun getData(uid: String): ReferralDataResponse
    suspend fun applyCode(refCode: String, uid: String): ApplyCodeResponse
}
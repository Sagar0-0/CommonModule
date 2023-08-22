package com.example.referral.repo

import com.example.referral.model.ApplyCodeResponse
import com.example.referral.model.ReferralDataResponse
import fit.asta.health.common.utils.ResponseState

interface ReferralRepo {
    suspend fun getData(uid: String): ResponseState<ReferralDataResponse>
    suspend fun applyCode(refCode: String, uid: String): ResponseState<ApplyCodeResponse>

}

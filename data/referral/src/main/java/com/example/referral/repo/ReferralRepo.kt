package com.example.referral.repo

import com.example.common.utils.ResponseState
import com.example.referral.model.ApplyCodeResponse
import com.example.referral.model.ReferralDataResponse

interface ReferralRepo {
    suspend fun getData(uid: String): ResponseState<ReferralDataResponse>
    suspend fun applyCode(refCode: String, uid: String): ResponseState<ApplyCodeResponse>

}

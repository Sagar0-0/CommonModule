package com.example.referral.repo

import com.example.referral.api.ReferralApi
import fit.asta.health.common.utils.getResponseState
import javax.inject.Inject

class ReferralRepoImpl
@Inject constructor(
    private val remoteApi: ReferralApi
) : ReferralRepo {
    override suspend fun getData(uid: String) = getResponseState { remoteApi.getData(uid) }


    override suspend fun applyCode(
        refCode: String,
        uid: String
    ) = getResponseState { remoteApi.applyCode(refCode, uid) }
}
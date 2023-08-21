package com.example.referral.repo

import android.util.Log
import com.example.common.utils.ResponseState
import com.example.referral.api.ReferralApi
import com.example.referral.model.ApplyCodeResponse
import com.example.referral.model.ReferralDataResponse
import javax.inject.Inject

class ReferralRepoImpl
@Inject constructor(
    private val remoteApi: ReferralApi
) : ReferralRepo {
    override suspend fun getData(uid: String): ResponseState<ReferralDataResponse> {
        return try {
            val response = remoteApi.getData(uid)
            ResponseState.Success(response)
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }

    override suspend fun applyCode(
        refCode: String,
        uid: String
    ): ResponseState<ApplyCodeResponse> {
        return try {
            val response = remoteApi.applyCode(refCode, uid)
            ResponseState.Success(response)
        } catch (e: Exception) {
            Log.e("REF", "applyCode: $e")
            ResponseState.Error(e)
        }
    }
}
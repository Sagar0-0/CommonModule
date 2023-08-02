package fit.asta.health.payments.referral.repo

import android.util.Log
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.referral.api.ReferralApi
import fit.asta.health.payments.referral.model.ApplyCodeResponse
import fit.asta.health.payments.referral.model.ReferralDataResponse
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
            when (response.status.code) {
                200 -> {
                    ResponseState.Success(response)
                }

                else -> {
                    ResponseState.ErrorResponse(response.status.code)
                }
            }
        } catch (e: Exception) {
            Log.e("REF", "applyCode: $e")
            ResponseState.Error(e)
        }
    }
}
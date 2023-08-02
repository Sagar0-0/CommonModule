package health.payments.referral.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.referral.model.ApplyCodeResponse
import fit.asta.health.payments.referral.model.ReferralDataResponse
import fit.asta.health.payments.referral.repo.ReferralRepo

class FakeReferralRepoImpl : ReferralRepo {
    private var isError = false
    fun setError(value: Boolean) {
        isError = value
    }

    override suspend fun getData(uid: String): ResponseState<ReferralDataResponse> {
        return if (isError) {
            ResponseState.Error(Exception("Error"))
        } else {
            ResponseState.Success(ReferralDataResponse())
        }
    }

    override suspend fun applyCode(
        refCode: String,
        uid: String
    ): ResponseState<ApplyCodeResponse> {
        TODO("Not yet implemented")
    }
}
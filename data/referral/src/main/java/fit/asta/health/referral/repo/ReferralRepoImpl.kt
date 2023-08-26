package fit.asta.health.referral.repo

import fit.asta.health.common.utils.getResponseState
import fit.asta.health.referral.remote.ReferralApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReferralRepoImpl
@Inject constructor(
    private val remoteApi: ReferralApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReferralRepo {
    override suspend fun getData(uid: String) = withContext(coroutineDispatcher) {
        getResponseState { remoteApi.getData(uid) }
    }


    override suspend fun applyCode(
        refCode: String,
        uid: String
    ) = withContext(coroutineDispatcher) {
        getResponseState { remoteApi.applyCode(refCode, uid) }
    }
}
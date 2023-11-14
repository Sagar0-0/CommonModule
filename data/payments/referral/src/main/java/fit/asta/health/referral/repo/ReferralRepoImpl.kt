package fit.asta.health.referral.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.referral.remote.ReferralApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReferralRepoImpl
@Inject constructor(
    private val remoteApi: ReferralApi,
    private val errorHandler: ReferralErrorHandler,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReferralRepo {
    override suspend fun getData(uid: String) = withContext(coroutineDispatcher) {
        getApiResponseState(errorHandler = errorHandler) { remoteApi.getData(uid) }
    }
}
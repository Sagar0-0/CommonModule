package fit.asta.health.discounts.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.discounts.remote.CouponsApi
import fit.asta.health.discounts.remote.model.CouponRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CouponsRepoImpl @Inject constructor(
    private val remoteApi: CouponsApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CouponsRepo {

    override suspend fun getCouponCodeDetails(couponRequest: CouponRequest) =
        withContext(coroutineDispatcher) {
            getApiResponseState { remoteApi.getCouponCodeDetails(couponRequest) }
        }
}


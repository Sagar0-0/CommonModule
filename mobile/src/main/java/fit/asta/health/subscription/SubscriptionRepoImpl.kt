package fit.asta.health.subscription

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.subscription.data.SubscriptionData
import fit.asta.health.subscription.data.SubscriptionDataMapper
import fit.asta.health.subscription.data.SubscriptionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SubscriptionRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: SubscriptionDataMapper
) : SubscriptionRepo {

    override suspend fun fetchSubscriptionPlans(): Flow<SubscriptionData> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getSubscriptionPlans()))
        }
    }

    override suspend fun fetchSubscriptionStatus(userId: String): Flow<SubscriptionStatus> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getSubscriptionStatus(userId)))
        }
    }
}
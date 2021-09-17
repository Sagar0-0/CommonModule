package fit.asta.health.subscription

import fit.asta.health.subscription.data.SubscriptionData
import fit.asta.health.subscription.data.SubscriptionStatus
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepo {
    suspend fun fetchSubscriptionPlans(): Flow<SubscriptionData>
    suspend fun fetchSubscriptionStatus(userId: String): Flow<SubscriptionStatus>
}
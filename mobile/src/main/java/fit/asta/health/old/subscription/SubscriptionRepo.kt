package fit.asta.health.old.subscription

import fit.asta.health.old.subscription.data.SubscriptionData
import fit.asta.health.old.subscription.data.SubscriptionStatus
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepo {
    suspend fun fetchSubscriptionPlans(): Flow<SubscriptionData>
    suspend fun fetchSubscriptionStatus(userId: String): Flow<SubscriptionStatus>
}
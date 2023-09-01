package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.datastore.UserPreferencesData
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepo {

    val userData: Flow<UserPreferencesData>
    suspend fun getData(
        uid: String,
        country: String
    ): ResponseState<SubscriptionResponse>
}

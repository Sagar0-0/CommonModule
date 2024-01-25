package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.subscription.remote.model.SubscriptionDurationsData
import fit.asta.health.subscription.remote.model.SubscriptionFinalAmountData
import fit.asta.health.subscription.remote.model.SubscriptionPlansResponse
import fit.asta.health.subscription.remote.model.UserSubscribedPlan

interface SubscriptionRepo {

    suspend fun getFinalAmountData(
        type: String,
        categoryId: String,
        productId: String,
        country: String = "IND"
    ): ResponseState<SubscriptionFinalAmountData>

    suspend fun getSubscriptionData(
        country: String = "IND",
        uid: String
    ): ResponseState<SubscriptionPlansResponse>

    suspend fun getUserSubscribedPlan(
        uid: String
    ): ResponseState<UserSubscribedPlan>


    suspend fun getSubscriptionDurationsData(
        categoryId: String,
        country: String = "IND",
    ): ResponseState<SubscriptionDurationsData>
}

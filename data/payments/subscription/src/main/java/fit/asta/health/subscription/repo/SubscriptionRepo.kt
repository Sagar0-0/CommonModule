package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.subscription.remote.model.SubscriptionCategoryData
import fit.asta.health.subscription.remote.model.SubscriptionDurationsData
import fit.asta.health.subscription.remote.model.SubscriptionFinalAmountData

interface SubscriptionRepo {

    suspend fun getFinalAmountData(
        type: String,
        categoryId: String,
        productId: String,
        country: String = "IND"
    ): ResponseState<SubscriptionFinalAmountData>

    suspend fun getSubscriptionData(
        country: String = "IND"
    ): ResponseState<List<SubscriptionCategoryData>>


    suspend fun getSubscriptionDurationsData(
        categoryId: String,
        country: String = "IND",
    ): ResponseState<SubscriptionDurationsData>
}

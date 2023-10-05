package fit.asta.health.discounts.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.discounts.remote.model.DiscountsData

interface DiscountsRepo {
    suspend fun getData(): ResponseState<List<DiscountsData>>
}
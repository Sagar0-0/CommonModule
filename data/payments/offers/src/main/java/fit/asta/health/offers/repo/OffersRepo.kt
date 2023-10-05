package fit.asta.health.offers.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.offers.remote.model.OffersData

interface OffersRepo {
    suspend fun getData(): ResponseState<List<OffersData>>
}
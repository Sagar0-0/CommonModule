package fit.asta.health.common

import fit.asta.health.common.data.MultiSelectData
import kotlinx.coroutines.flow.Flow

interface MultiSelectRepo {
    fun fetchSelectionData(uid: String): Flow<List<MultiSelectData>>
}
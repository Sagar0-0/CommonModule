package fit.asta.health.common.multiselect

import fit.asta.health.common.multiselect.data.MultiSelectData
import kotlinx.coroutines.flow.Flow

interface MultiSelectRepo {
    fun fetchSelectionData(uid: String): Flow<List<MultiSelectData>>
}
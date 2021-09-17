package fit.asta.health.common

import fit.asta.health.common.data.MultiSelectData
import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MultiSelectRepoImpl(private val remoteApis: RemoteApis,
                          private val dataMapper: MultiSelectDataMapper): MultiSelectRepo {
    override fun fetchSelectionData(uid: String): Flow<List<MultiSelectData>> {
        return flow {
            val dataList = remoteApis.getMultiSelectionData(uid)
            emit(dataMapper.toMap(dataList))
        }
    }
}
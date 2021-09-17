package fit.asta.health.navigation.home.categories

import fit.asta.health.navigation.home.categories.data.CategoryData
import fit.asta.health.navigation.home.categories.data.CategoryDataMapper
import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoriesRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: CategoryDataMapper
) : CategoriesRepo {

    override suspend fun fetchCategories(type: String): Flow<List<CategoryData>> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getCategories(type)))
        }
    }
}
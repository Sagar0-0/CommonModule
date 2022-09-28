package fit.asta.health.navigation.home_old.categories

import fit.asta.health.navigation.home_old.categories.data.CategoryData
import kotlinx.coroutines.flow.Flow

interface CategoriesRepo {
    suspend fun fetchCategories(type: String): Flow<List<CategoryData>>
}
package fit.asta.health.navigation.home_old.categories

import fit.asta.health.navigation.home_old.categories.data.CategoryData


interface CategoriesDataStore {
    fun updateList(list: List<CategoryData>)
    fun getCategory(position: Int): CategoryData
}
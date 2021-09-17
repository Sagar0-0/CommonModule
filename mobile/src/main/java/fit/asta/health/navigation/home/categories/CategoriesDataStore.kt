package fit.asta.health.navigation.home.categories

import fit.asta.health.navigation.home.categories.data.CategoryData


interface CategoriesDataStore {
    fun updateList(list: List<CategoryData>)
    fun getCategory(position: Int): CategoryData
}
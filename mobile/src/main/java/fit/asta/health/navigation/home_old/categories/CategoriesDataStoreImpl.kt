package fit.asta.health.navigation.home_old.categories

import fit.asta.health.navigation.home_old.categories.data.CategoryData


class CategoriesDataStoreImpl :
    CategoriesDataStore {
    private var listCategories: List<CategoryData> = listOf()

    override fun updateList(list: List<CategoryData>) {
        listCategories = list
    }

    override fun getCategory(position: Int): CategoryData {
        return listCategories[position]
    }
}
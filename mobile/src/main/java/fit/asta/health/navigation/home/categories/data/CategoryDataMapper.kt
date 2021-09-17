package fit.asta.health.navigation.home.categories.data

import fit.asta.health.navigation.home.categories.networkdata.CategoriesNetData

class CategoryDataMapper {

    fun toMap(categoriesNetData: CategoriesNetData): List<CategoryData> {
        return categoriesNetData.data.map {
            CategoryData().apply {
                uid = it.uid
                title = it.ttl
                imgUrl = it.url
            }
        }
    }
}
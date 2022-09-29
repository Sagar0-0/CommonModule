package fit.asta.health.navigation.home_old.categories.networkdata


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class CategoriesNetData(
    @SerializedName("statusDTO")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: List<CategoryNetData> = listOf()
)
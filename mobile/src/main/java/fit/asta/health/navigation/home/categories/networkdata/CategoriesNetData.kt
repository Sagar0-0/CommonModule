package fit.asta.health.navigation.home.categories.networkdata


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class CategoriesNetData(
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: List<CategoryNetData> = listOf()
)
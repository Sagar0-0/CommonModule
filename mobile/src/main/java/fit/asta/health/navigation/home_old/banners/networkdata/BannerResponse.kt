package fit.asta.health.navigation.home_old.banners.networkdata


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class BannerResponse(
    @SerializedName("statusDTO")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: List<BannerNetData> = listOf()
)
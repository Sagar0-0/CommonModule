package fit.asta.health.navigation.home.banners.networkdata


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class BannerResponse(
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: List<BannerNetData> = listOf()
)
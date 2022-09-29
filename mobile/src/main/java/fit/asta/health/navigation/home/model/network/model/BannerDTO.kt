package fit.asta.health.navigation.home.model.network.model

import com.google.gson.annotations.SerializedName

data class BannerDTO(
    @SerializedName("dsc")
    val desc: String,
    @SerializedName("id")
    val idBanner: String,
    @SerializedName("tid")
    val tid: String,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val urlBanner: String,
    @SerializedName("vis")
    val vis: Boolean
)

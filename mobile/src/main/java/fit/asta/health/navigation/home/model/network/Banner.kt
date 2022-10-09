package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("dsc")
    val desc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("vis")
    val isVisible: Boolean
)

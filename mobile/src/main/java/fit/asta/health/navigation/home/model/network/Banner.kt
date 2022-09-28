package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("dsc")
    val desc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("tid")
    val tid: String,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("vis")
    val vis: Boolean
)

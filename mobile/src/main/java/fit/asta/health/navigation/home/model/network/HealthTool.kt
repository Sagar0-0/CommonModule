package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class HealthTool(
    @SerializedName("dsc")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("url")
    val urlImage: String
)

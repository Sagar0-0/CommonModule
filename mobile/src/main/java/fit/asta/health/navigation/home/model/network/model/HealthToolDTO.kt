package fit.asta.health.navigation.home.model.network.model

import com.google.gson.annotations.SerializedName

data class HealthToolDTO(
    @SerializedName("dsc")
    val description: String,
    @SerializedName("id")
    val idHealthTool: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val codeHealthTool: Int,
    @SerializedName("ttl")
    val titleHealthTool: String,
    @SerializedName("url")
    val urlImage: String
)

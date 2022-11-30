package fit.asta.health.profile.model.domain

import com.google.gson.annotations.SerializedName


data class HealthProperties(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("dsc")
    val description: String
)
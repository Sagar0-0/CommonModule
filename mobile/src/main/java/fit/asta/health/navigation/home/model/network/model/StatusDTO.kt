package fit.asta.health.navigation.home.model.network.model

import com.google.gson.annotations.SerializedName

data class StatusDTO(
    @SerializedName("code")
    val codeStatus: Int,
    @SerializedName("msg")
    val msg: String
)

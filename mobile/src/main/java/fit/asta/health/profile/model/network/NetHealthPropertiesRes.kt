package fit.asta.health.profile.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.domain.HealthProperties


data class NetHealthPropertiesRes(
    @SerializedName("status") val status: Status,
    @SerializedName("data") val healthProperties: ArrayList<HealthProperties>,
)

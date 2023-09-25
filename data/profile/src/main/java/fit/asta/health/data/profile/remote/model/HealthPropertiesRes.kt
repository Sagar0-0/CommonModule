package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class HealthPropertiesRes(
    @SerializedName("status") val status: Status,
    @SerializedName("data") val healthProperties: ArrayList<HealthProperties>,
)

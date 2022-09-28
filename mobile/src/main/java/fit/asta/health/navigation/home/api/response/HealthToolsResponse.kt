package fit.asta.health.navigation.home.api.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.home.model.network.Status
import fit.asta.health.navigation.home.model.network.HealthTools

data class HealthToolsResponse(
    @SerializedName("status")
    var status: Status,
    @SerializedName("data")
    var data: HealthTools,
)
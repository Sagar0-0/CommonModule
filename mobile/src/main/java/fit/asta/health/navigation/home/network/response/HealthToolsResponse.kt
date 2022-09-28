package fit.asta.health.navigation.home.network.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.home.network.model.Status
import fit.asta.health.navigation.home.network.model.HealthTools

data class HealthToolsResponse(
    @SerializedName("status")
    var status: Status,
    @SerializedName("data")
    var data: HealthTools,
)
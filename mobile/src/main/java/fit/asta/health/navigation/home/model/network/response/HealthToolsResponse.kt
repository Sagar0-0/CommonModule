package fit.asta.health.navigation.home.model.network.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.home.model.network.model.StatusDTO
import fit.asta.health.navigation.home.model.network.model.HealthTools

data class HealthToolsResponse(
    @SerializedName("statusDTO")
    var statusDTO: StatusDTO,
    @SerializedName("data")
    var data: HealthTools,
)
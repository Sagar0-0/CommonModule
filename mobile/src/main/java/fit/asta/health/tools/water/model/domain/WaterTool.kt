package fit.asta.health.tools.water.model.domain

import com.google.gson.annotations.SerializedName
import fit.asta.health.tools.water.model.network.ProgressData
import fit.asta.health.tools.water.model.network.WaterToolData

data class WaterTool(
    var waterToolData: WaterToolData,
    var progressData: ProgressData,
    var selectedListId :List<String>,
    var beveragesDetails: List<BeverageDetails>
)

data class BeverageDetails(
    var beverageId: String,
    var title: String,
    var name: String,
    var rank: Int,
    var unit: String,
    var containers: MutableList<Int>,
    var qty: Int,
    var icon: String,
    var code: String,
    var isSelected: Boolean,
)

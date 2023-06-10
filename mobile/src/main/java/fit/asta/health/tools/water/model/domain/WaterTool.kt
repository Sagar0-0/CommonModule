package fit.asta.health.tools.water.model.domain

import fit.asta.health.tools.water.model.network.ButterMilk
import fit.asta.health.tools.water.model.network.Coconut
import fit.asta.health.tools.water.model.network.FruitJuice
import fit.asta.health.tools.water.model.network.Meta
import fit.asta.health.tools.water.model.network.Milk
import fit.asta.health.tools.water.model.network.TodayActivityData
import fit.asta.health.tools.water.model.network.Water
import fit.asta.health.tools.water.model.network.WaterToolData

data class WaterTool(
    var waterToolData: WaterToolData,
    val uid: String,
    val butterMilk: ButterMilk,
    val coconut: Coconut,
    val fruitJuice: FruitJuice,
    val milk: Milk,
    val water: Water,
    val meta: Meta,
    val time: String,
    val todayActivityData: MutableList<TodayActivityData>,
    var beveragesDetails: MutableList<BeverageDetails>
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
)

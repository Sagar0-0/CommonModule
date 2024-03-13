package fit.asta.health.data.water.remote.model

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
    var beveragesDetails: MutableList<BeverageDetailsData>
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
data class BeverageDetailsData(
    var beverageId: String,
    var title: String,
    var name: String,
    var rank: Int,
    var unit: String,
    var count: Int,
    var icon: String,
    var code: String,
)
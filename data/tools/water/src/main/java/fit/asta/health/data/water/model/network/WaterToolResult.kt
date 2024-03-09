package fit.asta.health.data.water.model.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.data.water.db.dbmodel.Status

data class WaterToolResult(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
)

data class Data(
    @SerializedName("beverageList")
    val beverageList: List<Beverage>,
    @SerializedName("progressData")
    val progressData: ProgressData,
    @SerializedName("todayActivityData")
    val todayActivityData: List<TodayActivityData>? = null,
    @SerializedName("userBeverageInfo")
    val userBeverageInfo: UserBeverageInfo,
    @SerializedName("waterToolData")
    val waterToolData: WaterToolData
)

data class Beverage(
    @SerializedName("code")
    val code: String,
    @SerializedName("con")
    val containerList: List<Int>,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("ttl")
    val title: String
)

data class ProgressData(
    @SerializedName("bMilk")
    val butterMilk: ButterMilk,
    @SerializedName("coco")
    val coconut: Coconut,
    @SerializedName("fj")
    val fruitJuice: FruitJuice,
    @SerializedName("id")
    val id: String,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("milk")
    val milk: Milk,
    @SerializedName("time")
    val time: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("water")
    val water: Water
)

data class ButterMilk(
    @SerializedName("con")
    val consume: Double = 0.0,
    @SerializedName("rcm")
    val recommend: Double = 0.0,
    @SerializedName("rem")
    val remaining: Double = 0.0,
    @SerializedName("tgt")
    val target: Double = 0.0,
)

data class Coconut(
    @SerializedName("con")
    val consume: Double = 0.0,
    @SerializedName("rcm")
    val recommend: Double = 0.0,
    @SerializedName("rem")
    val remaining: Double = 0.0,
    @SerializedName("tgt")
    val target: Double = 0.0
)

data class FruitJuice(
    @SerializedName("con")
    val consume: Double = 0.0,
    @SerializedName("rcm")
    val recommend: Double = 0.0,
    @SerializedName("rem")
    val remaining: Double = 0.0,
    @SerializedName("tgt")
    val target: Double = 0.0
)

data class Milk(
    @SerializedName("con")
    val consume: Double = 0.0,
    @SerializedName("rcm")
    val recommend: Double = 0.0,
    @SerializedName("rem")
    val remaining: Double = 0.0,
    @SerializedName("tgt")
    val target: Double = 0.0
)

data class Water(
    @SerializedName("con")
    val consume: Double = 0.0,
    @SerializedName("rcm")
    val recommend: Double = 0.0,
    @SerializedName("rem")
    val remaining: Double = 0.0,
    @SerializedName("tgt")
    val target: Double = 0.0,
)

data class Meta(
    @SerializedName("max")
    val max: String = "6",
    @SerializedName("min")
    val min: String = "0"
)

data class TodayActivityData(
    @SerializedName("bev")
    val bev: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("qty")
    val qty: Double,
    @SerializedName("time")
    val time: String,
    @SerializedName("uid")
    val uid: String
)

data class UserBeverageInfo(
    @SerializedName("bev")
    val bev: List<BeverageInfo>,
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String
)

data class Bev(
    @SerializedName("code")
    val code: String,
    @SerializedName("con")
    val containerList: List<Int>,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("unit")
    val unit: String
)

data class BeverageInfo(
    @SerializedName("code")
    val code: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("unit")
    val unit: String
)

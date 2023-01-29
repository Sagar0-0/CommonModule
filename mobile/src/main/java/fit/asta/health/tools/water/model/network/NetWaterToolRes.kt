package fit.asta.health.tools.water.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetWaterToolRes(
    @SerializedName("data")
    val waterTool: NetWaterTool,
    @SerializedName("status")
    val status: Status
)

data class NetWaterTool(
    @SerializedName("waterToolData")
    val waterToolData: WaterToolData,
    @SerializedName("progressData")
    val progressData: ProgressData,
    @SerializedName("userBeverageInfo")
    val userBeverageInfo: WaterBeverageInfo,
    @SerializedName("beverageList")
    val allBeveragesList:List<AllBeverageData>
)

data class ProgressData(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("tgt")
    val goal: Int,
    @SerializedName("con")
    val consumed: Int,
    @SerializedName("rcm")
    val recommendation: Double,
    @SerializedName("rem")
    val remaining: Int,
    @SerializedName("meta")
    val meta:Meta
)

data class Meta(
    @SerializedName("min")
    val min: String,
    @SerializedName("max")
    val max: String,
)

data class AllBeverageData(
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("con")
    val containers: List<Int>,
    @SerializedName("icon")
    val icon:String,
    @SerializedName("code")
    val code: String
)
data class WaterToolData(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("tgt")
    val tgt: String,
    @SerializedName("prc")
    val prc: Any,
    @SerializedName("wea")
    val wea: Boolean
)

data class WaterBeverageInfo(
    @SerializedName("bev")
    val userBeverageList: List<UserBeverageData>,
)

data class UserBeverageData(
    @SerializedName("id")
    val beverageId: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("con")
    val containers: List<Int>,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("code")
    val code: String,
)
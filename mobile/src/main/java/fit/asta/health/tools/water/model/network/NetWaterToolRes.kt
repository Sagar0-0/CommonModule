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
    @SerializedName("waterBeverageInfo")
    val waterBeverageInfo: WaterBeverageInfo,
    @SerializedName("waterToolData")
    val waterToolData: WaterToolData
)

data class WaterToolData(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: Any,
    @SerializedName("tgt")
    val tgt: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)

data class WaterBeverageInfo(
    val bev: List<Bev>,
    val id: String,
    val qty: Qty,
    val uid: String
)

data class Qty(
    val meta: Meta,
    val value: String
)

data class Bev(
    val code: String,
    val icon: String,
    val name: String,
    //qty
    //meta
    val ttl: String
)

data class Meta(
    val max: String,
    val min: String
)
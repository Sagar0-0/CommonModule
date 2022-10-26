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
    @SerializedName("progressData")
    val progressData: ProgressData,
    @SerializedName("quantityData")
    val quantityData: List<QuantityData>,
    @SerializedName("waterBeverageInfo")
    val waterBeverageInfo: WaterBeverageInfo
)

data class ProgressData(
    @SerializedName("code")
    val code: String,
    @SerializedName("dtl")
    val dtl: Dtl,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: Any?,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)

data class QuantityData(
    @SerializedName("bev")
    val bev: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("qty")
    val qty: Double,
    @SerializedName("time")
    val time: String,
    @SerializedName("tsid")
    val tsid: String,
    @SerializedName("uid")
    val uid: String
)

data class WaterBeverageInfo(
    @SerializedName("bev")
    val bev: List<Bev>,
    @SerializedName("id")
    val id: String,
    @SerializedName("qty")
    val qty: Qty,
    @SerializedName("uid")
    val uid: String
)

data class Dtl(
    @SerializedName("con")
    val con: Double,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("rcm")
    val rcm: Double,
    @SerializedName("rem")
    val rem: Double,
    @SerializedName("tgt")
    val tgt: Int
)

data class Meta(
    @SerializedName("max")
    val max: String,
    @SerializedName("min")
    val min: String
)

data class Bev(
    @SerializedName("code")
    val code: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("ttl")
    val ttl: String
)

data class Qty(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("value")
    val value: String
)
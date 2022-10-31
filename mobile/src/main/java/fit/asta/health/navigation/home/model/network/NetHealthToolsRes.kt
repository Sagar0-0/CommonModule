package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetHealthToolsRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val healthTools: NetHealthTools
)

data class NetHealthTools(
    @SerializedName("bnr")
    val netBanners: List<NetBanner>,
    @SerializedName("slot")
    val sunSlots: NetSunSlots,
    @SerializedName("tml")
    val testimonials: List<NetTestimonial>,
    @SerializedName("tool")
    val tools: List<NetHealthTool>,
    @SerializedName("ust")
    val selectedTools: NetSelectedTools,
    @SerializedName("wtr")
    val weather: NetWeather
)

class NetSelectedTools(
    @SerializedName("uid")
    val userId: String,
    @SerializedName("tid")
    val tools: List<String>
)
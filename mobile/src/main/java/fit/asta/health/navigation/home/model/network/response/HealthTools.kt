package fit.asta.health.navigation.home.model.network.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.home.model.network.*

data class HealthTools(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val data: Data? = null,
)

data class Data(
    @SerializedName("bnr")
    val banners: List<Banner>,
    @SerializedName("slot")
    val sunSlots: SunSlots,
    @SerializedName("tml")
    val testimonials: List<Testimonial>,
    @SerializedName("tool")
    val tools: List<HealthTool>,
    @SerializedName("wtr")
    val weather: Weather,
)
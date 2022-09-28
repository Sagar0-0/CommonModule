package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class HealthTools(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val data: Data,
)

data class Data(
    @SerializedName("bnr")
    val banners: List<Banner>,
    @SerializedName("tml")
    val testimonials: List<Testimonial>,
    @SerializedName("tool")
    val tools: List<HealthTool>,
    @SerializedName("wtr")
    val weather: Weather
)
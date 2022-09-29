package fit.asta.health.navigation.home.model.network.model

import com.google.gson.annotations.SerializedName

data class HealthTools(
    @SerializedName("statusDTO")
    val statusDTO: StatusDTO,
    @SerializedName("data")
    val data: Data,
)

data class Data(
    @SerializedName("bnr")
    val bannerDTOS: List<BannerDTO>,
    @SerializedName("tml")
    val testimonials: List<Testimonial>,
    @SerializedName("tool")
    val tools: List<HealthToolDTO>,
    @SerializedName("wtr")
    val weather: Weather
)
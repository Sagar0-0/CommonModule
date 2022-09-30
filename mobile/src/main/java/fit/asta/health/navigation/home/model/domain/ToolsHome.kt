package fit.asta.health.navigation.home.model.domain


data class ToolsHome(
    //val status: Status,
    val banners: List<Banner>? = null,
    val weather: Weather? = null,
    val tools: List<HealthTool>? = null,
    val testimonials: List<Testimonial>? = null
)
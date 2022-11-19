package fit.asta.health.navigation.home.model.domain

import fit.asta.health.testimonials.model.domain.Testimonial


data class ToolsHome(
    //val status: Status,
    val banners: List<Banner>? = null,
    val weather: Weather? = null,
    val sunSlots: List<SunSlot>? = null,
    val tools: List<HealthTool>? = null,
    val testimonials: List<Testimonial>? = null
)
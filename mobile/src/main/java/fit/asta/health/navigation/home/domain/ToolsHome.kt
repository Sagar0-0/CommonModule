package fit.asta.health.navigation.home.domain

import fit.asta.health.navigation.home.model.network.model.*


data class ToolsHome(


    //Banner
    val desc: String,
    val idBanner: String,
    val tid: String,
    val ttl: String,
    val type: Int,
    val urlBanner: String,
    val vis: Boolean,

    //HealthDTO
    val description: String,
    val idHealthTool: String,
    val name: String,
    val codeHealthTool: Int,
    val titleHealthTool: String,
    val urlImage: String,

    //StatusDTO
    val codeStatus: Int,
    val msg: String,

    //TestimonialsDTO
    val idTestimonials: String,
    val approve: Boolean,
    val rank: Int,
    val titleTestimonials: String,
    val text: String,
    val media: List<Media>,
    val user: User,

    //WeatherDTO

    val idWeather: String,
    val date: String,
    val temperature: String,
    val location: String,
    val sunRise: String,
    val sunSet: String,
    val urlWeather: String,
    val wUrl: String,
    val sunSlots: List<SunSlot>,

    //HealthToolsDTO
    val statusDTO: StatusDTO,
    val data: Data,
)
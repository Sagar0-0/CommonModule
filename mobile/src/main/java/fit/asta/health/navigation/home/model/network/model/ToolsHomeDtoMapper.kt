package fit.asta.health.navigation.home.model.network.model

import fit.asta.health.navigation.home.domain.ToolsHome
import fit.asta.health.navigation.home.model.network.response.HealthToolsResponse
import fit.asta.health.utils.DomainMapper

class ToolsHomeDtoMapper : DomainMapper<HealthToolsResponse, ToolsHome> {

    override fun mapToDomainModel(model: HealthToolsResponse): ToolsHome {
        return ToolsHome(
            desc = model.data.data.bannerDTOS[0].desc,
            idBanner = model.data.data.bannerDTOS[0].idBanner,
            tid = model.data.data.bannerDTOS[0].tid,
            ttl = model.data.data.bannerDTOS[0].ttl,
            type = model.data.data.bannerDTOS[0].type,
            urlBanner = model.data.data.bannerDTOS[0].urlBanner,
            vis = model.data.data.bannerDTOS[0].vis,

            description = model.data.data.tools[0].description,
            idHealthTool = model.data.data.tools[0].idHealthTool,
            name = model.data.data.tools[0].name,
            codeHealthTool = model.data.data.tools[0].codeHealthTool,
            titleHealthTool = model.data.data.tools[0].titleHealthTool,
            urlImage = model.data.data.tools[0].urlImage,

            codeStatus = model.statusDTO.codeStatus,
            msg = model.statusDTO.msg,
            idTestimonials = model.data.data.testimonials[0].idTestimonials,
            approve = model.data.data.testimonials[0].approve,
            rank = model.data.data.testimonials[0].rank,
            titleTestimonials = model.data.data.testimonials[0].titleTestimonials,
            text = model.data.data.testimonials[0].text,
            media = model.data.data.testimonials[0].media,
            user = model.data.data.testimonials[0].user,

            idWeather = model.data.data.weather.idWeather,
            date = model.data.data.weather.date,
            temperature = model.data.data.weather.temperature,
            location = model.data.data.weather.location,
            sunRise = model.data.data.weather.sunRise,
            sunSet = model.data.data.weather.sunSet,
            urlWeather = model.data.data.weather.urlWeather,
            wUrl = model.data.data.weather.wUrl,
            sunSlots = model.data.data.weather.sunSlots,

            statusDTO = model.statusDTO,
            data = model.data.data
        )
    }

    override fun mapFromDomainModel(domainModel: ToolsHome): HealthToolsResponse {
        TODO()
        //return HealthToolsResponse()
    }

    fun toDomainList(initial: List<HealthToolsResponse>): List<ToolsHome> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<ToolsHome>): List<ToolsHome> {
        TODO()
        //return initial.map { mapFromDomainModel(it) }
    }
}
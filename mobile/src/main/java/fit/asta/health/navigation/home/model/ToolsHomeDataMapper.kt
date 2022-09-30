package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.*
import fit.asta.health.navigation.home.model.network.response.HealthTools
import fit.asta.health.utils.DomainMapper

class ToolsHomeDataMapper : DomainMapper<HealthTools, ToolsHome> {

    override fun mapToDomainModel(networkModel: HealthTools): ToolsHome {
        return ToolsHome(
            banners = networkModel.data.banners.map {
                Banner(
                    id = it.id,
                    type = it.type,
                    title = it.title,
                    desc = it.desc,
                    url = it.url,
                    isVisible = it.isVisible
                )
            },
            weather = Weather(
                id = networkModel.data.weather.id,
                date = networkModel.data.weather.date,
                temperature = networkModel.data.weather.temperature,
                location = networkModel.data.weather.location,
                sunRise = networkModel.data.weather.sunRise,
                sunSet = networkModel.data.weather.sunSet,
                url = networkModel.data.weather.url,
                weatherUrl = networkModel.data.weather.weatherUrl,
                sunSlots = networkModel.data.weather.sunSlots.map {
                    SunSlot(
                        id = it.id,
                        time = it.time,
                        temperature = it.temperature
                    )
                }
            ),
            tools = networkModel.data.tools.map {
                HealthTool(
                    id = it.id,
                    name = it.name,
                    code = it.code,
                    title = it.title,
                    description = it.description,
                    url = it.url
                )
            },
            testimonials = networkModel.data.testimonials.map {
                Testimonial(
                    id = it.id,
                    title = it.title,
                    text = it.text,
                    rank = it.rank,
                    media = it.media.map { media ->
                        Media(
                            type = media.type,
                            title = media.title,
                            url = media.url
                        )
                    },
                    user = User(
                        userId = it.user.userId,
                        name = it.user.name,
                        org = it.user.org,
                        role = it.user.role,
                        url = it.user.url
                    )
                )
            }
        )
    }

    override fun mapFromDomainModel(domainModel: ToolsHome): HealthTools {
        TODO()
    }

    fun toDomainList(initial: List<HealthTools>): List<ToolsHome> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<ToolsHome>): List<HealthTools> {
        return initial.map { mapFromDomainModel(it) }
    }
}
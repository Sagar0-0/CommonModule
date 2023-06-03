package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.Banner
import fit.asta.health.navigation.home.model.domain.HealthTool
import fit.asta.health.navigation.home.model.domain.ToolsHome
import fit.asta.health.navigation.home.model.domain.Weather
import fit.asta.health.navigation.home.model.network.NetHealthToolsRes
import fit.asta.health.testimonials.model.TestimonialDataMapper


class ToolsHomeDataMapper(private val testimonialDataMapper: TestimonialDataMapper) {

    fun mapToDomainModel(networkModel: NetHealthToolsRes): ToolsHome {
        return ToolsHome(
            banners = networkModel.healthTools.netBanners.map {
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
                id = "",
                sunRise = "",
                sunSet = "",
                temperature = "",
                location = "",
                date = "",
                imgUrl = "",
                weatherType = null,
                air = null
            ),
//            sunSlots = SunSlot(
//
//            ),
            tools = networkModel.healthTools.tools.map {
                HealthTool(
                    id = it.id,
                    name = it.name,
                    code = it.code,
                    title = it.title,
                    description = it.description,
                    url = it.url
                )
            },
            testimonials = testimonialDataMapper.mapToDomainModel(networkModel.healthTools.testimonials)
        )
    }

    fun mapToNetworkModel(domainModel: ToolsHome): NetHealthToolsRes {
        TODO("Not yet implemented")
    }

    fun toDomainList(initial: List<NetHealthToolsRes>): List<ToolsHome> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<ToolsHome>): List<NetHealthToolsRes> {
        return initial.map { mapToNetworkModel(it) }
    }
}

/*
private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun toWeatherDataMap(weather: SunSlots?): Map<Int, List<SunSlot>> {

    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun toWeatherInfo(weather: Weather): Weather {

    val weatherDataMap = toWeatherDataMap(null)
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}
*/
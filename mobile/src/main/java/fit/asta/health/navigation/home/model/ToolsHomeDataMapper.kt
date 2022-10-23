package fit.asta.health.navigation.home.model

import fit.asta.health.navigation.home.model.domain.*
import fit.asta.health.navigation.home.model.network.response.NetHealthToolsRes


class ToolsHomeDataMapper {

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
//                date = networkModel.data.weather.date,
//                temperature = networkModel.data.weather.temperature,
//                location = networkModel.data.weather.loc,
//                sunRise = networkModel.data.weather.sunrise,
//                sunSet = networkModel.data.weather.sunSet,
//                url = networkModel.data.weather.url,
//                weatherUrl = networkModel.data.weather.weatherUrl
            ),
            /*sunSlots = SunSlot(
                    id = it.id,
                    time = it.time,
                    temperature = it.temperature
                ),*/
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
            testimonials = networkModel.healthTools.testimonials.map {
                Testimonial(
                    id = it.id,
                    userId = it.userId,
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
                        name = it.user.name,
                        org = it.user.org,
                        role = it.user.role,
                        url = it.user.url
                    )
                )
            }
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
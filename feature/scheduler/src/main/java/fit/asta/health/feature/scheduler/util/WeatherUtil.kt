package fit.asta.health.feature.scheduler.util

import fit.asta.health.resources.drawables.R as DrawR

object WeatherUtil {
    fun getWeatherIcon(code: Int): Int {
        return when (code) {
            0 -> {
                DrawR.drawable.weather_sun
            }

            in 1..3 -> {
                DrawR.drawable.weather_2_partly_cloudy_day
            }

            in 45..48 -> {
                DrawR.drawable.weather_2_fog
            }

            in 51..55 -> {
                DrawR.drawable.weather_2_cloudy
            }

            in 56..57 -> {
                DrawR.drawable.weather_2_freezing_drizzle
            }

            in 61..65 -> {
                DrawR.drawable.weather_sun_cloud_angled_rain
            }

            in 71..73 -> {
                DrawR.drawable.weather_2_mod_snow
            }

            77 -> {
                DrawR.drawable.weather_snow
            }

            in 80..82 -> {
                DrawR.drawable.rainimage
            }

            in 85..86 -> {
                DrawR.drawable.ic_snowy
            }

            95 -> {
                DrawR.drawable.weather_2_cloud_rain_thunder
            }

            in 96..99 -> {
                DrawR.drawable.weather_2_cloud_sleet_snow_thunder
            }

            else -> {
                DrawR.drawable.weather_2_cloudy
            }
        }
    }
}
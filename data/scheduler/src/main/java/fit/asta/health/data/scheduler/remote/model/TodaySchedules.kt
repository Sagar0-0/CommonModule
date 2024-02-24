package fit.asta.health.data.scheduler.remote.model

import com.google.gson.annotations.SerializedName


data class TodaySchedules(
    @SerializedName("wtr")
    val weather: Weather = Weather(),
    @SerializedName("slot")
    val slot: Slots? = null
) {
    data class Weather(
        @SerializedName("current_weather")
        val currentWeather: CurrentWeather = CurrentWeather(),
        @SerializedName("daily")
        val daily: Daily = Daily(),
        @SerializedName("daily_units")
        val dailyUnits: DailyUnits = DailyUnits(),
        @SerializedName("date")
        val date: String = "",
        @SerializedName("elevation")
        val elevation: Int = 0,
        @SerializedName("generationtime_ms")
        val generationTimeMs: Double = 0.0,
        @SerializedName("latitude")
        val latitude: Double = 0.0,
        @SerializedName("loc")
        val loc: String = "",
        @SerializedName("longitude")
        val longitude: Double = 0.0,
        @SerializedName("time")
        val time: String = "",
        @SerializedName("timezone")
        val timezone: String = "",
        @SerializedName("timezone_abbreviation")
        val timezoneAbbreviation: String = "",
        @SerializedName("utc_offset_seconds")
        val utcOffsetSeconds: Int = 0 ,
        @SerializedName("temp")
        val temp: Double = 0.0,
        @SerializedName("wea")
        val weather: String ?= null,
        @SerializedName("code")
        val weatherCode: Int = 0,
    )

    data class DailyUnits(
        @SerializedName("sunrise")
        val sunrise: String = "",
        @SerializedName("sunset")
        val sunset: String = "",
        @SerializedName("time")
        val time: String = ""
    )

    data class Daily(
        @SerializedName("sunrise")
        val sunrise: List<String> = listOf(),
        @SerializedName("sunset")
        val sunset: List<String> = listOf(),
        @SerializedName("time")
        val time: List<String> = listOf()
    )

    data class CurrentWeather(
        @SerializedName("temperature")
        val temperature: Double = 0.0,
        @SerializedName("time")
        val time: String = "",
        @SerializedName("weathercode")
        val weatherCode: Int = 0,
        @SerializedName("winddirection")
        val windDirection: Int = 0,
        @SerializedName("windspeed")
        val windSpeed: Double = 0.0
    )

    data class Slots(
        @SerializedName("date")
        val date: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("lat")
        val latitude: String,
        @SerializedName("loc")
        val location: String,
        @SerializedName("lon")
        val longitude: String,
        @SerializedName("slot")
        val slot: List<Slot> ,
        @SerializedName("msg")
        val message: String?=null
    )

    data class Slot(
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("time")
        val time: String,
        @SerializedName("uv")
        val uv: Double
    )

}

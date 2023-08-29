package fit.asta.health.data.scheduler.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status


data class TodaySchedules(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Data(
        @SerializedName("wtr")
        val weather: Weather,
//        @SerializedName("schedule")
//        val schedule: List<AlarmEntity>,
        @SerializedName("slot")
        val slot: Slots? = null
    ) {
        data class Weather(
            @SerializedName("current_weather")
            val currentWeather: CurrentWeather,
            @SerializedName("daily")
            val daily: Daily,
            @SerializedName("daily_units")
            val dailyUnits: DailyUnits,
            @SerializedName("date")
            val date: String,
            @SerializedName("elevation")
            val elevation: Int,
            @SerializedName("generationtime_ms")
            val generationTimeMs: Double,
            @SerializedName("latitude")
            val latitude: Double,
            @SerializedName("loc")
            val loc: String,
            @SerializedName("longitude")
            val longitude: Double,
            @SerializedName("time")
            val time: String,
            @SerializedName("timezone")
            val timezone: String,
            @SerializedName("timezone_abbreviation")
            val timezoneAbbreviation: String,
            @SerializedName("utc_offset_seconds")
            val utcOffsetSeconds: Int
        )

        data class DailyUnits(
            @SerializedName("sunrise")
            val sunrise: String,
            @SerializedName("sunset")
            val sunset: String,
            @SerializedName("time")
            val time: String
        )

        data class Daily(
            @SerializedName("sunrise")
            val sunrise: List<String>,
            @SerializedName("sunset")
            val sunset: List<String>,
            @SerializedName("time")
            val time: List<String>
        )

        data class CurrentWeather(
            @SerializedName("temperature")
            val temperature: Double,
            @SerializedName("time")
            val time: String,
            @SerializedName("weathercode")
            val weatherCode: Int,
            @SerializedName("winddirection")
            val windDirection: Int,
            @SerializedName("windspeed")
            val windSpeed: Double
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
            val slot: List<Slot>
        )

        data class Slot(
            @SerializedName("temp")
            val temp: Double,
            @SerializedName("time")
            val time: String,
            @SerializedName("uv")
            val uv: Int
        )

    }
}
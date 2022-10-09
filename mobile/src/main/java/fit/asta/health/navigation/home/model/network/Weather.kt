package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current_weather")
    val current_weather: CurrentWeather,
    @SerializedName("daily")
    val daily: Daily,
    @SerializedName("daily_units")
    val daily_units: DailyUnits,
    @SerializedName("date")
    val date: String,
    @SerializedName("elevation")
    val elevation: Int,
    @SerializedName("generationtime_ms")
    val generationtime_ms: Double,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("loc")
    val loc: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_abbreviation")
    val timezone_abbreviation: String,
    @SerializedName("utc_offset_seconds")
    val utc_offset_seconds: Int
)

data class CurrentWeather(
    @SerializedName("temperature")
    val temperature: Double,
    @SerializedName("time")
    val time: String,
    @SerializedName("weathercode")
    val weathercode: Int,
    @SerializedName("winddirection")
    val winddirection: Int,
    @SerializedName("windspeed")
    val windspeed: Double
)

data class Daily(
    @SerializedName("sunrise")
    val sunrise: List<String>,
    @SerializedName("sunset")
    val sunset: List<String>,
    @SerializedName("time")
    val time: List<String>
)

data class DailyUnits(
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("time")
    val time: String
)
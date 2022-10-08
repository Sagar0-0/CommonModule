package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class SunSlots(
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("elevation")
    val elevation: Int,
    @SerializedName("generationtime_ms")
    val generationtime_ms: Double,
    @SerializedName("hourly")
    val hourly: Hourly,
    @SerializedName("hourly_units")
    val hourly_units: HourlyUnits,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("location")
    val location: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_abbreviation")
    val timezone_abbreviation: String,
    @SerializedName("utc_offset_seconds")
    val utc_offset_seconds: Int
)

data class Hourly(
    @SerializedName("temperature_2m")
    val temperature_2m: List<Double>,
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("weathercode")
    val weathercode: List<Int>
)

data class HourlyUnits(
    @SerializedName("temperature_2m")
    val temperature_2m: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("weathercode")
    val weathercode: String
)
package fit.asta.health.navigation.today.model

import com.google.gson.annotations.SerializedName


data class TodaySchedules(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Data(
        @SerializedName("schedule")
        val schedule: Any,
        @SerializedName("slot")
        val slot: Slot
    ) {
        data class Slot(
            @SerializedName("date")
            val date: String,
            @SerializedName("elevation")
            val elevation: Int,
            @SerializedName("generationtime_ms")
            val generationtimeMs: Double,
            @SerializedName("hourly")
            val hourly: Hourly,
            @SerializedName("hourly_units")
            val hourlyUnits: HourlyUnits,
            @SerializedName("latitude")
            val latitude: Double,
            @SerializedName("location")
            val location: String,
            @SerializedName("longitude")
            val longitude: Double,
            @SerializedName("timezone")
            val timezone: String,
            @SerializedName("timezone_abbreviation")
            val timezoneAbbreviation: String,
            @SerializedName("utc_offset_seconds")
            val utcOffsetSeconds: Int
        ) {
            data class Hourly(
                @SerializedName("temperature_2m")
                val temperature2m: List<Double>,
                @SerializedName("time")
                val time: List<String>,
                @SerializedName("weathercode")
                val weathercode: List<Int>
            )

            data class HourlyUnits(
                @SerializedName("temperature_2m")
                val temperature2m: String,
                @SerializedName("time")
                val time: String,
                @SerializedName("weathercode")
                val weathercode: String
            )
        }
    }

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )
}
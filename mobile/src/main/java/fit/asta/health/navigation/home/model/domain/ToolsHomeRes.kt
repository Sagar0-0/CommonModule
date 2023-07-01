package fit.asta.health.navigation.home.model.domain

import com.google.gson.annotations.SerializedName
import fit.asta.health.testimonials.model.domain.Testimonial


data class ToolsHomeRes(
    @SerializedName("data")
    val `data`: ToolsHome,
    @SerializedName("status")
    val status: Status
) {
    data class ToolsHome(
        @SerializedName("bnr")
        val banners: List<Banner>,
        @SerializedName("tml")
        val testimonials: List<Testimonial>,
        @SerializedName("tool")
        val tools: List<HealthTool>,
        @SerializedName("ust")
        val ust: Ust,
        @SerializedName("wtr")
        val wtr: Wtr
    ) {
        data class Banner(
            @SerializedName("dsc")
            val desc: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("ttl")
            val ttl: String,
            @SerializedName("type")
            val type: Int,
            @SerializedName("url")
            val url: String,
            @SerializedName("vis")
            val vis: Boolean
        )

        data class HealthTool(
            @SerializedName("code")
            val code: Int,
            @SerializedName("dsc")
            val desc: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("ttl")
            val title: String,
            @SerializedName("url")
            val url: String
        )

        data class Ust(
            @SerializedName("id")
            val id: String,
            @SerializedName("tid")
            val tid: List<String>,
            @SerializedName("uid")
            val uid: String
        )

        data class Wtr(
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
            val generationtimeMs: Double,
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
        ) {
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
        }
    }

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )
}
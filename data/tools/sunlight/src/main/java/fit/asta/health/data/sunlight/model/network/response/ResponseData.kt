package fit.asta.health.data.sunlight.model.network.response

import com.google.gson.annotations.SerializedName


data class ResponseData(
    @SerializedName("data")
    val `data`: SunlightToolData,
    @SerializedName("status")
    val status: Status
) {
    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )

    data class SunlightToolData(
        @SerializedName("sunlightData")
        val sunlightTool: SunlightTool,
        @SerializedName("sunSlotData")
        val sunSlotData: SunSlotData,
        @SerializedName("SunlightProgressData")
        val sunlightProgressData: SunlightProgressData
    ) {
        data class SunlightTool(
            @SerializedName("id")
            val id: String,
            @SerializedName("uid")
            val uid: String,
            @SerializedName("type")
            val type: Int,
            @SerializedName("code")
            val code: String,
            @SerializedName("prc")
            val prc: List<Prc>
        ) {
            data class Prc(
                @SerializedName("id")
                val id: String,
                @SerializedName("ttl")
                val ttl: String,
                @SerializedName("dsc")
                val dsc: String,
                @SerializedName("values")
                val values: List<Values>,
                @SerializedName("type")
                val type: Int,
                @SerializedName("code")
                val code: String
            ) {
                data class Values(
                    @SerializedName("id")
                    val id: String,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("value")
                    val value: String
                )
            }
        }

        data class SunSlotData(
            @SerializedName("latitude")
            val latitude: Double,
            @SerializedName("longitude")
            val longitude: Double,
            @SerializedName("location")
            val location: String,
            @SerializedName("date")
            val date: String,
            @SerializedName("generationtime_ms")
            val generationTimeMs: Double,
            @SerializedName("utc_offset_seconds")
            val utcOffsetSeconds: Int,
            @SerializedName("timezone")
            val timezone: String,
            @SerializedName("timezone_abbreviation")
            val timezoneAbbreviation: String,
            @SerializedName("elevation")
            val elevation: Int,
            @SerializedName("hourly_units")
            val hourlyUnits: HourlyUnits,
            @SerializedName("hourly")
            val hourly: Hourly
        ) {
            data class HourlyUnits(
                @SerializedName("time")
                val time: String,
                @SerializedName("temperature_2m")
                val temperature2m: String,
                @SerializedName("weathercode")
                val weathercode: String
            )

            data class Hourly(
                @SerializedName("time")
                val time: List<String>,
                @SerializedName("temperature_2m")
                val temperature2m: List<Double>,
                @SerializedName("weathercode")
                val weathercode: List<Int>
            )
        }

        data class SunlightProgressData(

            @SerializedName("id")
            val spdid: String,
            @SerializedName("uid")
            val spduid: String,
            @SerializedName("date")
            val date: String,
            @SerializedName("tgt")
            val tgt: Int,
            @SerializedName("ach")
            val ach: Int,
            @SerializedName("rem")
            val rem: Int,
            @SerializedName("rcm")
            val rcm: Double,
            @SerializedName("rcmIu")
            val rcmIu: Int,
            @SerializedName("achIu")
            val achIu: Int,
            @SerializedName("metIu")
            val metIu: Double,
        )
    }
}

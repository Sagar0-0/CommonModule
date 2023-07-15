package fit.asta.health.tools.sunlight.model.domain


data class SunlightTool(
    val id: String = "",
    val uid: String = "",
    val name: String = "",
    val code: String = "",
    val description: String = "",
    val sunscreenValue: String = "",
    val sunscreenTitle: String = "",
    val skincolorValue: String = "",
    val skincolorTitle: String = "",
    val skinexposureValue: String = "",
    val skinexposureTitle: String = "",
    val ageValue: String = "",
    val ageTitle: String = "",
    val latitude: String = "28.625",
    val longitude: String = "77.25",
    val location: String = "bangalore",
    val date: String = "2023-03-02",
    val generationTimeMs: Double = 0.0,
    val utcOffsetSeconds: Int = 0,
    val elevation: Int = 219,
    val hourlyUnits: HourlyUnits = HourlyUnits(),
    val time: List<String> = listOf(),
    val temperature2m: List<Double> = listOf(),
    val weathercode: List<Int> = listOf(),
    val spdid: String = "",
    val spduid: String = "",
    val tgt: Int = 15,
    val ach: Int = 20,
    val rem: Int = 0,
    val rcm: Double = 3.1691442737454016,
    val rcmIu: Int = 600,
    val achIu: Int = 23,
    val metIu: Double = 1.1691442737454019
) {
    data class HourlyUnits(
        val time: String = "iso8601",
        val temperature_2m: String = "°C",
        val weathercode: String = "wmo code"
    )
}
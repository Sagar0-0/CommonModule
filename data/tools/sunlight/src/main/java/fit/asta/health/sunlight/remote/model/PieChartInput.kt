package fit.asta.health.sunlight.remote.model

import android.graphics.Color


data class PieChartInput(
    val value: Int=1,
    val description: String,
    val time: String? = "",
    val isTapped: Boolean = false,
    val uv:String="",
    val temp:String=""
)
package fit.asta.health.feature.walking.view.session

import java.time.LocalDate

data class ProgressState(
    val date: LocalDate,
    val stepsTaken: Int,
    val targetDistance: Float,
    val targetDuration: Float,
    val duration: Int,
    val calorieBurned: Int,
    val distanceTravelled: Double,
    val carbonDioxideSaved: Double,
    val state: Boolean
)

fun formatDuration(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60

    return "%02d:%02d".format(hours, remainingMinutes)
}
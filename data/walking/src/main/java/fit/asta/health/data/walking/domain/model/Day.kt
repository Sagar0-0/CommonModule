package fit.asta.health.data.walking.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import fit.asta.health.data.walking.data.Settings
import fit.asta.health.data.walking.service.DailyFitnessModel
import java.time.LocalDate

@Entity(tableName = "day")
data class Day(

    @PrimaryKey(autoGenerate = false) val startupTime: Long,

    val date: LocalDate,

    val steps: Int = 0,

    val targetDistance: Float,

    val targetDuration: Int,

    val duration: Int,

    val state: Boolean,

    val height: Int = 188,

    val weight: Int = 70,

    val stepLength: Int = 72,

    val pace: Double = 1.0
) {

    companion object

    val distanceTravelled
        get() = run {
            val distanceCentimeters = steps * stepLength
            distanceCentimeters.toDouble() / 100_000
        }

    val calorieBurned
        get() = run {
            val modifier = height / 182.0 + weight / 70.0 - 1
            0.04 * steps * pace * modifier
        }

    val carbonDioxideSaved
        get() = run {
            steps * 0.1925 / 1000.0
        }
}

fun Settings.toDay(
    startupTime: Long,
    date: LocalDate,
    targetDistance: Float,
    targetDuration: Int,
    state: Boolean = true
) = Day(
    startupTime = startupTime,
    date = date,
    steps = 0,
    duration = 0,
    state = state,
    targetDistance = targetDistance,
    targetDuration = targetDuration,
    height = height,
    weight = weight,
    stepLength = stepLength,
    pace = pace
)

fun Day.toDailyFitness() = DailyFitnessModel(
    stepCount = steps,
    caloriesBurned = calorieBurned.toInt(),
    distance = distanceTravelled.toFloat()
)

package fit.asta.health.tools.walking.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import fit.asta.health.tools.walking.core.data.Settings
import fit.asta.health.tools.walking.nav.DailyFitnessModel
import java.time.LocalDate

@Entity(tableName = "day")
data class Day(

    @PrimaryKey(autoGenerate = false) val startupTime: Long,

    val date: LocalDate,

    val steps: Int = 0,

    val goal: Int,

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
    steps: Int = 0,
    duration: Int = 0,
    state: Boolean = true
) = Day(
    startupTime = startupTime,
    date = date,
    steps = steps,
    duration = duration,
    state = state,
    goal = dailyGoal,
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

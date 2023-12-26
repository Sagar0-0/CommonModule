package fit.asta.health.data.walking.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "daily")
data class AllDay(

    @PrimaryKey val date: LocalDate,

    val steps: Int = 0,

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
}
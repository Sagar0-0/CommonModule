package fit.asta.health.data.exercise.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercise_data")
data class ExerciseData(
    @PrimaryKey val date: Int,
    @ColumnInfo(name = "appliedAngleDistanceDance") val appliedAngleDistanceDance: Float,
    @ColumnInfo(name = "appliedAngleDistanceYoga") val appliedAngleDistanceYoga: Float,
    @ColumnInfo(name = "appliedAngleDistanceWorkout") val appliedAngleDistanceWorkout: Float,
    @ColumnInfo(name = "appliedAngleDistanceHiit") val appliedAngleDistanceHiit: Float,
    @ColumnInfo(name = "startDance") val startDance:Boolean,
    @ColumnInfo(name = "startYoga") val startYoga:Boolean,
    @ColumnInfo(name = "startWorkout") val startWorkout:Boolean,
    @ColumnInfo(name = "startHiit") val startHiit:Boolean,
    @ColumnInfo(name = "timeDance") val timeDance:Long,
    @ColumnInfo(name = "timeYoga") val timeYoga:Long,
    @ColumnInfo(name = "timeWorkout") val timeWorkout:Long,
    @ColumnInfo(name = "timeHiit") val timeHiit:Long,
)

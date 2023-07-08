package fit.asta.health.tools.exercise.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ExerciseData::class], version = 1)
abstract class ExerciseToolDatabase : RoomDatabase() {
    abstract fun exerciseDao():ExerciseDao
}
package fit.asta.health.data.water.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WaterData::class], version = 1)
abstract class WaterToolDatabase : RoomDatabase() {
    abstract fun waterDao(): WaterDao
}
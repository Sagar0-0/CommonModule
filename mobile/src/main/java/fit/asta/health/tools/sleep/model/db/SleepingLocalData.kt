package fit.asta.health.tools.sleep.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleeping_data")
data class SleepingLocalData(
    @PrimaryKey val userId: String
)

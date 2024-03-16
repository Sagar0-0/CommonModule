package fit.asta.health.data.water.local

import androidx.room.Database
import androidx.room.RoomDatabase
import fit.asta.health.data.water.local.entity.BevQuantityConsumed
import fit.asta.health.data.water.local.entity.BevDataDetails
import fit.asta.health.data.water.local.entity.ConsumptionHistory
import fit.asta.health.data.water.local.entity.Goal
import fit.asta.health.data.water.local.entity.History


@Database(
    entities = [History::class, BevDataDetails::class, ConsumptionHistory::class,
        Goal::class, BevQuantityConsumed::class], version = 1
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
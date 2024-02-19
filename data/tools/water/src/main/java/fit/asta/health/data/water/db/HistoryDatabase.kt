package fit.asta.health.data.water.db

import androidx.room.Database
import androidx.room.RoomDatabase
import fit.asta.health.data.water.check.model.BevDataDetails
import fit.asta.health.data.water.check.model.ConsumptionHistory
import fit.asta.health.data.water.check.model.Goal
import fit.asta.health.data.water.check.model.History


@Database(entities = [History::class, BevDataDetails::class,ConsumptionHistory::class,
                     Goal::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao() : HistoryDao
}
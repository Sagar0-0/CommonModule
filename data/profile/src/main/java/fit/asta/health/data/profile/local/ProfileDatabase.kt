package fit.asta.health.data.profile.local

import androidx.room.Database
import androidx.room.RoomDatabase
import fit.asta.health.data.profile.local.entity.ProfileEntity

@Database(
    entities = [ProfileEntity::class],
    version = 1
)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun getDao(): ProfileDao
}
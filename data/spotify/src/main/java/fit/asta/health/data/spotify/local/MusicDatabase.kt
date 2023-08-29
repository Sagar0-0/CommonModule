package fit.asta.health.data.spotify.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.common.Track

@Database(entities = [Track::class, Album::class], version = 1)
@TypeConverters(CustomTypeConvertor::class)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}
package fit.asta.health.thirdparty.spotify.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.thirdparty.spotify.data.model.common.Track
import fit.asta.health.thirdparty.spotify.data.model.common.Album


@Database(entities = [Track::class, Album::class], version = 1)
@TypeConverters(CustomTypeConvertor::class)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}
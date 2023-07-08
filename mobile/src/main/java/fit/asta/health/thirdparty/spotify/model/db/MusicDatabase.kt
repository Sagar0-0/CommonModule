package fit.asta.health.thirdparty.spotify.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.common.Album


@Database(entities = [Track::class, Album::class], version = 1)
@TypeConverters(CustomTypeConvertor::class)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}
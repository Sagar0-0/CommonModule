package fit.asta.health.thirdparty.spotify.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX


@Database(entities = [TrackEntity::class, AlbumX::class], version = 1)
@TypeConverters(CustomTypeConvertor::class)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}
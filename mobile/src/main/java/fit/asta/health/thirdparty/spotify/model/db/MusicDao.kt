package fit.asta.health.thirdparty.spotify.model.db

import androidx.room.*
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Query("SELECT * FROM fav_tracks_table")
    fun getAll(): Flow<List<TrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Update
    suspend fun updateTrack(trackEntity: TrackEntity)

    @Delete
    suspend fun deleteTrack(trackEntity: TrackEntity)

    @Query("DELETE FROM fav_tracks_table")
    suspend fun deleteAllTracks()

    @Query("SELECT * FROM fav_albums_table")
    fun getAllAlbums(): Flow<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)

    @Update
    suspend fun updateAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Query("DELETE FROM fav_albums_table")
    suspend fun deleteAllAlbums()
}
package fit.asta.health.data.spotify.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fit.asta.health.data.spotify.remote.model.common.Album
import fit.asta.health.data.spotify.remote.model.common.Track

@Dao
interface MusicDao {//TODO: Use flows for all Query requests

    @Query("SELECT * FROM fav_tracks_table")
    fun getAllTracks(): List<Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track)

    @Update
    suspend fun updateTrack(track: Track)

    @Delete
    suspend fun deleteTrack(track: Track)

    @Query("DELETE FROM fav_tracks_table")
    suspend fun deleteAllTracks()

    @Query("SELECT * FROM fav_albums_table")
    fun getAllAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)

    @Update
    suspend fun updateAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Query("DELETE FROM fav_albums_table")
    suspend fun deleteAllAlbums()
}
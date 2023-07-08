package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.db.MusicDao
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val musicDao: MusicDao
) {

    fun getAllTracks(): Flow<List<Track>> {
        return musicDao.getAll()
    }

    suspend fun insertTrack(track: Track) {
        musicDao.insertTrack(track)
    }

    suspend fun updateTrack(track: Track) {
        musicDao.updateTrack(track)
    }

    suspend fun deleteTrack(track: Track) {
        musicDao.deleteTrack(track)
    }

    suspend fun deleteAllTrack() {
        musicDao.deleteAllTracks()
    }

    fun getAllAlbums(): Flow<List<Album>> {
        return musicDao.getAllAlbums()
    }

    suspend fun insertAlbum(album: Album) {
        musicDao.insertAlbum(album)
    }

    suspend fun updateAlbum(album: Album) {
        musicDao.updateAlbum(album)
    }

    suspend fun deleteAlbum(album: Album) {
        musicDao.deleteAlbum(album)
    }

    suspend fun deleteAllAlbum() {
        musicDao.deleteAllTracks()
    }

}
package fit.asta.health.thirdparty.spotify.model.db

import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val musicDao: MusicDao
) {

    fun getAllTracks(): List<Track> {
        return musicDao.getAll()
    }

    suspend fun insertTrack(track: Track) {
        musicDao.insertTrack(track)
    }

    suspend fun deleteTrack(track: Track) {
        musicDao.deleteTrack(track)
    }

    fun getAllAlbums(): List<Album> {
        return musicDao.getAllAlbums()
    }

    suspend fun insertAlbum(album: Album) {
        musicDao.insertAlbum(album)
    }

    suspend fun deleteAlbum(album: Album) {
        musicDao.deleteAlbum(album)
    }
}
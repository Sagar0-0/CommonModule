package fit.asta.health.thirdparty.spotify.model.db

import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import javax.inject.Inject

class MusicDataSourceImpl @Inject constructor(
    private val musicDao: MusicDao
) : MusicDataSource {

    override fun getAllTracks(): List<Track> {
        return musicDao.getAll()
    }

    override suspend fun insertTrack(track: Track) {
        musicDao.insertTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        musicDao.deleteTrack(track)
    }

    override fun getAllAlbums(): List<Album> {
        return musicDao.getAllAlbums()
    }

    override suspend fun insertAlbum(album: Album) {
        musicDao.insertAlbum(album)
    }

    override suspend fun deleteAlbum(album: Album) {
        musicDao.deleteAlbum(album)
    }
}
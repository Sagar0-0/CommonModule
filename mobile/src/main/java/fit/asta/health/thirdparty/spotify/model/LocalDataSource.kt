package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.db.MusicDao
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val musicDao: MusicDao
) {

    fun getAllTracks(): Flow<List<TrackEntity>> {
        return musicDao.getAll()
    }

    suspend fun insertTrack(track: TrackEntity) {
        musicDao.insertTrack(track)
    }

    suspend fun updateTrack(track: TrackEntity) {
        musicDao.updateTrack(track)
    }

    suspend fun deleteTrack(track: TrackEntity) {
        musicDao.deleteTrack(track)
    }

    suspend fun deleteAllTrack() {
        musicDao.deleteAllTracks()
    }

    fun getAllAlbums(): Flow<List<AlbumX>> {
        return musicDao.getAllAlbums()
    }

    suspend fun insertAlbum(album: AlbumX) {
        musicDao.insertAlbum(album)
    }

    suspend fun updateAlbum(album: AlbumX) {
        musicDao.updateAlbum(album)
    }

    suspend fun deleteAlbum(album: AlbumX) {
        musicDao.deleteAlbum(album)
    }

    suspend fun deleteAllAlbum() {
        musicDao.deleteAllTracks()
    }

}
package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.db.MusicDao
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val musicDao: MusicDao
) {

    fun getAllTracks(): Flow<SpotifyNetworkCall<List<Track>>> {
        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = musicDao.getAll()
            emit(SpotifyNetworkCall.Success(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertTrack(track: Track) {
        musicDao.insertTrack(track)
    }

    suspend fun deleteTrack(track: Track) {
        musicDao.deleteTrack(track)
    }

    fun getAllAlbums(): Flow<SpotifyNetworkCall<List<Album>>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = musicDao.getAllAlbums()
            emit(SpotifyNetworkCall.Success(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertAlbum(album: Album) {
        musicDao.insertAlbum(album)
    }

    suspend fun deleteAlbum(album: Album) {
        musicDao.deleteAlbum(album)
    }
}
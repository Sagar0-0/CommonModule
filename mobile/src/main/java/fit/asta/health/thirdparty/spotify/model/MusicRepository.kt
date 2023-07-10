package fit.asta.health.thirdparty.spotify.model

import dagger.hilt.android.scopes.ActivityRetainedScoped
import fit.asta.health.thirdparty.spotify.model.db.LocalDataSource
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class MusicRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) {

    fun getAllTracks(): Flow<SpotifyNetworkCall<List<Track>>> {
        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = localDataSource.getAllTracks()
            emit(SpotifyNetworkCall.Success(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertTrack(track: Track) {
        localDataSource.insertTrack(track)
    }

    suspend fun deleteTrack(track: Track) {
        localDataSource.deleteTrack(track)
    }

    fun getAllAlbums(): Flow<SpotifyNetworkCall<List<Album>>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = localDataSource.getAllAlbums()
            emit(SpotifyNetworkCall.Success(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertAlbum(album: Album) {
        localDataSource.insertAlbum(album)
    }

    suspend fun deleteAlbum(album: Album) {
        localDataSource.deleteAlbum(album)
    }
}
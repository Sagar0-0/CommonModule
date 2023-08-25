package fit.asta.health.thirdparty.spotify.data.repo

import dagger.hilt.android.scopes.ActivityRetainedScoped
import fit.asta.health.thirdparty.spotify.data.local.MusicDao
import fit.asta.health.thirdparty.spotify.data.model.common.Album
import fit.asta.health.thirdparty.spotify.data.model.common.Track
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ActivityRetainedScoped
class MusicRepositoryImpl(
    private val localDataSource: MusicDao
) : MusicRepository {

    override fun getAllTracks(): Flow<SpotifyNetworkCall<List<Track>>> {
        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = localDataSource.getAllTracks()
            emit(SpotifyNetworkCall.Success(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertTrack(track: Track) {
        localDataSource.insertTrack(track)
    }

    override suspend fun deleteTrack(track: Track) {
        localDataSource.deleteTrack(track)
    }

    override fun getAllAlbums(): Flow<SpotifyNetworkCall<List<Album>>> {

        return flow {
            emit(SpotifyNetworkCall.Loading())
            val response = localDataSource.getAllAlbums()
            emit(SpotifyNetworkCall.Success(response))
        }.catch {
            emit(SpotifyNetworkCall.Failure(message = it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertAlbum(album: Album) {
        localDataSource.insertAlbum(album)
    }

    override suspend fun deleteAlbum(album: Album) {
        localDataSource.deleteAlbum(album)
    }
}
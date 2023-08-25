package fit.asta.health.thirdparty.spotify.data.repo

import dagger.hilt.android.scopes.ActivityRetainedScoped
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.datastore.IODispatcher
import fit.asta.health.thirdparty.spotify.data.local.MusicDao
import fit.asta.health.thirdparty.spotify.data.model.common.Album
import fit.asta.health.thirdparty.spotify.data.model.common.Track
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ActivityRetainedScoped
class MusicRepositoryImpl(
    private val musicDao: MusicDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MusicRepository {

    override suspend fun getAllTracks(): ResponseState<List<Track>> {
        return withContext(dispatcher) {
            getResponseState { musicDao.getAllTracks() }
        }
    }

    override suspend fun insertTrack(track: Track): ResponseState<Unit> {
        return withContext(dispatcher) {
            getResponseState { musicDao.insertTrack(track) }
        }
    }

    override suspend fun deleteTrack(track: Track): ResponseState<Unit> {
        return withContext(dispatcher) {
            getResponseState { musicDao.deleteTrack(track) }
        }
    }

    override suspend fun getAllAlbums(): ResponseState<List<Album>> {
        return withContext(dispatcher) {
            getResponseState { musicDao.getAllAlbums() }
        }
    }

    override suspend fun insertAlbum(album: Album): ResponseState<Unit> {
        return withContext(dispatcher) {
            getResponseState { musicDao.insertAlbum(album) }
        }
    }

    override suspend fun deleteAlbum(album: Album): ResponseState<Unit> {
        return withContext(dispatcher) {
            getResponseState { musicDao.deleteAlbum(album) }
        }
    }
}
package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    fun getAllTracks(): Flow<SpotifyNetworkCall<List<Track>>>

    suspend fun insertTrack(track: Track)

    suspend fun deleteTrack(track: Track)

    fun getAllAlbums(): Flow<SpotifyNetworkCall<List<Album>>>

    suspend fun insertAlbum(album: Album)

    suspend fun deleteAlbum(album: Album)
}
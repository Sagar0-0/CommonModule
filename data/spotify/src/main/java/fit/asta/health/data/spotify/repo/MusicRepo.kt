package fit.asta.health.data.spotify.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.spotify.remote.model.common.Album
import fit.asta.health.data.spotify.remote.model.common.Track

interface MusicRepo {

    suspend fun getAllTracks(): ResponseState<List<Track>>

    suspend fun insertTrack(track: Track): ResponseState<Unit>

    suspend fun deleteTrack(track: Track): ResponseState<Unit>

    suspend fun getAllAlbums(): ResponseState<List<Album>>

    suspend fun insertAlbum(album: Album): ResponseState<Unit>

    suspend fun deleteAlbum(album: Album): ResponseState<Unit>
}
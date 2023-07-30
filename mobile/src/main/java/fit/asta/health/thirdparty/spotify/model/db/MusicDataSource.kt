package fit.asta.health.thirdparty.spotify.model.db

import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.common.Track

interface MusicDataSource {

    fun getAllTracks(): List<Track>

    suspend fun insertTrack(track: Track)

    suspend fun deleteTrack(track: Track)

    fun getAllAlbums(): List<Album>

    suspend fun insertAlbum(album: Album)

    suspend fun deleteAlbum(album: Album)
}
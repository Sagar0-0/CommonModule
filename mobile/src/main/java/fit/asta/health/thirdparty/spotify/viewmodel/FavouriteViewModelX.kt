package fit.asta.health.thirdparty.spotify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.thirdparty.spotify.model.db.MusicRepository
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModelX @Inject constructor(
    private val repository: MusicRepository,
    application: Application
) : AndroidViewModel(application) {

    /**
     * This variable contains details of all the Tracks calls and states
     */
    private val _allTracks = MutableStateFlow<SpotifyNetworkCall<List<TrackEntity>>>(
        SpotifyNetworkCall.Initialized()
    )
    val allTracks = _allTracks.asStateFlow()

    /**
     * This variable contains details of all the albums calls and states
     */
    private val _allAlbums = MutableStateFlow<SpotifyNetworkCall<List<AlbumX>>>(
        SpotifyNetworkCall.Initialized()
    )
    val allAlbums = _allAlbums.asStateFlow()

    /**
     * This function fetches all the track from the local repository
     */
    fun getAllTracks() {

        _allTracks.value = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            repository.local.getAllTracks().catch { exception ->
                _allTracks.value = SpotifyNetworkCall.Failure(
                    message = exception.message
                )
            }.collect {
                _allTracks.value = SpotifyNetworkCall.Success(
                    data = it
                )
            }
        }
    }

    /**
     * This function fetches all the albums from the local repository
     */
    fun getAllAlbums() {

        _allAlbums.value = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            repository.local.getAllAlbums().catch { exception ->
                _allAlbums.value = SpotifyNetworkCall.Failure(
                    message = exception.message
                )
            }.collect {
                _allAlbums.value = SpotifyNetworkCall.Success(
                    data = it
                )
            }
        }
    }

    /**
     * This function is used to insert a Track into the Database
     */
    fun insertTrack(track: TrackEntity) {
        viewModelScope.launch {
            repository.local.insertTrack(track)
            getAllTracks()
        }
    }

    /**
     * This function is used to insert a Album into the Database
     */
    fun insertAlbum(album: AlbumX) {
        viewModelScope.launch {
            repository.local.insertAlbum(album)
            getAllAlbums()
        }
    }

//    fun updateTrack(track: TrackEntity) = viewModelScope.launch {
//        repository.local.updateTrack(track)
//    }
//
//    fun deleteTrack(track: TrackEntity) = viewModelScope.launch {
//        repository.local.deleteTrack(track)
//    }
//
//    fun deleteAllTracks() = viewModelScope.launch {
//        repository.local.deleteAllTrack()
//    }
//
//
//    fun updateAlbum(album: Album) = viewModelScope.launch {
//        repository.local.updateAlbum(album)
//    }
//
//    fun deleteAlbum(album: Album) = viewModelScope.launch {
//        repository.local.deleteAlbum(album)
//    }
//
//    fun deleteAllAlbums() = viewModelScope.launch {
//        repository.local.deleteAllAlbum()
//    }
}
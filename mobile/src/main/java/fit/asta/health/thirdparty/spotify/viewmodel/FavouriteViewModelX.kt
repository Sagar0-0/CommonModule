package fit.asta.health.thirdparty.spotify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.thirdparty.spotify.model.db.MusicRepository
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModelX @Inject constructor(
    private val repository: MusicRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _allTracks = MutableStateFlow<SpotifyNetworkCall<List<TrackEntity>>>(
        SpotifyNetworkCall.Initialized()
    )
    val allTracks = _allTracks.asStateFlow()


    var allAlbums: LiveData<List<Album>> = repository.local.getAllAlbums().asLiveData()

    val tag: String = FavoriteViewModelX::class.java.simpleName

    fun getAllTracks() {

        _allTracks.value = SpotifyNetworkCall.Loading()

        viewModelScope.launch {
            repository.local.getAllTracks().catch {
                _allTracks.value = SpotifyNetworkCall.Failure(
                    message = it.message
                )
            }.collect {
                _allTracks.value = SpotifyNetworkCall.Success(
                    data = it
                )
            }
        }
    }


//    fun insertTrack(track: TrackEntity) = viewModelScope.launch {
//        repository.local.insertTrack(track)
//    }
//
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
//    fun insertAlbum(album: Album) = viewModelScope.launch {
//        repository.local.insertAlbum(album)
//    }
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
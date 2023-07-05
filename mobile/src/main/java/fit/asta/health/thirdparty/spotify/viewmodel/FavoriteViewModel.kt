package fit.asta.health.thirdparty.spotify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.thirdparty.spotify.model.db.MusicRepository
import fit.asta.health.thirdparty.spotify.model.db.entity.TrackEntity
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MusicRepository,
    application: Application
) : AndroidViewModel(application) {
    var allTracks: LiveData<List<TrackEntity>> = repository.local.getAllTracks().asLiveData()

    fun insertTrack(track: TrackEntity) = viewModelScope.launch {
        repository.local.insertTrack(track)
    }

    fun updateTrack(track: TrackEntity) = viewModelScope.launch {
        repository.local.updateTrack(track)
    }

    fun deleteTrack(track: TrackEntity) = viewModelScope.launch {
        repository.local.deleteTrack(track)
    }

    fun deleteAllTracks() = viewModelScope.launch {
        repository.local.deleteAllTrack()
    }

    var allAlbums: LiveData<List<AlbumX>> = repository.local.getAllAlbums().asLiveData()

    fun insertAlbum(album: AlbumX) = viewModelScope.launch {
        repository.local.insertAlbum(album)
    }

    fun updateAlbum(album: AlbumX) = viewModelScope.launch {
        repository.local.updateAlbum(album)
    }

    fun deleteAlbum(album: AlbumX) = viewModelScope.launch {
        repository.local.deleteAlbum(album)
    }

    fun deleteAllAlbums() = viewModelScope.launch {
        repository.local.deleteAllAlbum()
    }
}
package fit.asta.health.player.jetpack_audio.presentation.screens.home.discover

import fit.asta.health.player.jetpack_audio.domain.data.Song

data class DiscoverViewState(
    val selectedAlbum: List<Song> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

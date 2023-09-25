package fit.asta.health.meditation.view.music

import fit.asta.health.player.domain.model.Song

data class MusicViewState(
    val selectedAlbum: List<Song> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

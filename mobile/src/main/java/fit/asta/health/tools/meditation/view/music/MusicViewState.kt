package fit.asta.health.tools.meditation.view.music

import fit.asta.health.player.jetpack_audio.domain.data.Song

data class MusicViewState(
    val selectedAlbum: List<Song> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

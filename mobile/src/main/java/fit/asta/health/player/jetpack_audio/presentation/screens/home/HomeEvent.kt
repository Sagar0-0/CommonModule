package fit.asta.health.player.jetpack_audio.presentation.screens.home

sealed class HomeEvent {
    data class OnSelectTab(val category: HomeCategory) : HomeEvent()
}

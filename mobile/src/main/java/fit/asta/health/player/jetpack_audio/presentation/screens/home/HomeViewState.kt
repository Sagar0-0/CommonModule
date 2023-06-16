package fit.asta.health.player.jetpack_audio.presentation.screens.home

data class HomeViewState(
    val refreshing: Boolean = false,
    val selectedCategory: HomeCategory = HomeCategory.Discover,
    val categories: List<HomeCategory> = HomeCategory.values().asList(),
    val errorMessage: String? = null
)

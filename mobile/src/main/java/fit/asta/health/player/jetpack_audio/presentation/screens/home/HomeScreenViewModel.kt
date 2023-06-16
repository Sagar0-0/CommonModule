package fit.asta.health.player.jetpack_audio.presentation.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject



@HiltViewModel
class HomeScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState>
        get() = _state

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSelectTab -> {
                _state.value = _state.value.copy(
                    selectedCategory = event.category
                )
            }
        }
    }
}

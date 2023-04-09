package fit.asta.health.scheduler.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.compose.screen.UiState
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.db.AlarmRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SchedulerViewModel
@Inject constructor(
    private val repository: AlarmRepository,
    private val backendRepo: AlarmBackendRepo,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(){
    lateinit var state: StateFlow<UiState>

    init {
//        viewModelScope.launch {
//            state = savedStateHandle.getStateFlow(loginScreenStateKey, UiState()).stateIn(
//                scope = viewModelScope
//            )
//        }
    }

}
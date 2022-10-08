package fit.asta.health.navigation.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.navigation.home.intent.HomeState
import fit.asta.health.navigation.home.model.ToolsHomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val toolsHomeRepo: ToolsHomeRepository
) : ViewModel() {

    private val _uiState: MutableState<HomeState?> = mutableStateOf(HomeState.Loading)
    val uiState: State<HomeState?>
        get() = _uiState

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            toolsHomeRepo.getHomeData("62fcd8c098eb9d5ed038b563", "").catch { exception ->
                _uiState.value = HomeState.Error(exception)
            }
                .collect {
                    _uiState.value = HomeState.Success(it)
                }
        }
    }
}
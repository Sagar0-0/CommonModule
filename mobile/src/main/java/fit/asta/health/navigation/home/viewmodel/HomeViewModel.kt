package fit.asta.health.navigation.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.navigation.home.intent.HomeAction
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

    private val _uiState: MutableState<HomeAction?> = mutableStateOf(null)
    val uiState: State<HomeAction?>
        get() = _uiState

    fun loadHomeData() {
        viewModelScope.launch {
            toolsHomeRepo.getHomeData("course", "").catch { exception ->
                _uiState.value = HomeAction.Error(exception)
            }
                .collect {
                    _uiState.value = HomeAction.LoadHomeData(it)
                }
        }
    }
}
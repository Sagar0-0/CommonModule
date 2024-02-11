package fit.asta.health.navigation.tools.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.common.utils.getNextDate
import fit.asta.health.common.utils.toUiState
import fit.asta.health.datastore.PrefManager
import fit.asta.health.home.remote.model.ToolsHome
import fit.asta.health.home.repo.ToolsHomeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ToolsHomeViewModel @Inject constructor(
    private val toolsHomeRepo: ToolsHomeRepo,
    private val prefManager: PrefManager,
    @UID private val uid: String
) : ViewModel() {

    private val _toolsHomeDataState = MutableStateFlow<UiState<ToolsHome>>(UiState.Loading)
    val toolsHomeDataState = _toolsHomeDataState.asStateFlow()

    fun loadHomeData() {
        _toolsHomeDataState.update {
            UiState.Loading
        }
        viewModelScope.launch {
            prefManager.address.collectLatest { address ->
                _toolsHomeDataState.update {
                    toolsHomeRepo.getHomeData(
                        userId = uid,
                        latitude = address.lat.toString(),
                        longitude = address.long.toString(),
                        location = address.currentAddress,
                        startDate = getCurrentDate(),
                        endDate = getNextDate(2),
                        time = getCurrentTime()
                    ).toUiState()
                }
            }

        }
    }
}

package fit.asta.health.navigation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.common.utils.getNextDate
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.navigation.home.model.ToolsHomeRepo
import fit.asta.health.navigation.home.model.network.NetSelectedTools
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toolsHomeRepo: ToolsHomeRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _mutableState = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _mutableState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {

            authRepo.getUser()?.let {
                toolsHomeRepo.getHomeData(
                    userId = it.uid,
                    latitude = "28.6353",
                    longitude = "77.2250",
                    location = "bangalore",
                    startDate = getCurrentDate(),
                    endDate = getNextDate(2),
                    time = getCurrentTime()
                ).catch { exception ->
                    _mutableState.value = HomeState.Error(exception)
                }.collect {
                    _mutableState.value = HomeState.Success(it)
                }
            }
        }
    }

    fun updateSelectedTools(toolIds: List<String>) {
        viewModelScope.launch {
            toolsHomeRepo.updateSelectedTools(
                NetSelectedTools(
                    userId = "62fcd8c098eb9d5ed038b563",
                    tools = toolIds
                )
            ).catch { exception ->
                _mutableState.value = HomeState.Error(exception)
            }.collect {
                //
            }
        }
    }

}
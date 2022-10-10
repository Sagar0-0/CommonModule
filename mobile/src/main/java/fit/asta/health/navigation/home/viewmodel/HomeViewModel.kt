package fit.asta.health.navigation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.navigation.home.intent.HomeState
import fit.asta.health.navigation.home.model.ToolsHomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val toolsHomeRepo: ToolsHomeRepository,
) : ViewModel() {

//    private val _uiState: MutableState<HomeState?> = mutableStateOf(HomeState.Loading)
//    val uiState: State<HomeState?>
//        get() = _uiState

    val mutableState = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            toolsHomeRepo.getHomeData(userId = "62fcd8c098eb9d5ed038b563",
                latitude = "28.6353",
                longitude = "77.2250",
                location = "bangalore",
                startDate = "2022-10-03",
                endDate = "2022-10-05",
                time = "2022-10-03%2012%20pm").catch { exception ->
                mutableState.value = HomeState.Error(exception)
            }.collect {
                mutableState.value = HomeState.Success(it)
            }
        }
    }
}
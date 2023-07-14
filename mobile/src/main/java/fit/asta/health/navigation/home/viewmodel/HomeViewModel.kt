package fit.asta.health.navigation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.common.utils.getNextDate
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.navigation.home.model.ToolsHomeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


//@ExperimentalCoroutinesApi
//@HiltViewModel
//class HomeViewModel @Inject constructor(
//    private val toolsHomeRepo: ToolsHomeRepo,
//    private val authRepo: AuthRepo,
//) : ViewModel() {
//
//    private val _mutableState = MutableStateFlow<HomeState>(HomeState.Loading)
//    val state = _mutableState.asStateFlow()
//
//    init {
//        loadHomeData()
//    }
//
//    fun loadHomeData() {
//        viewModelScope.launch {
//
//            authRepo.getUser()?.let { it ->
//                toolsHomeRepo.getHomeData(
//                    userId = it.uid,
//                    latitude = "28.6353",
//                    longitude = "77.2250",
//                    location = "bangalore",
//                    startDate = getCurrentDate(),
//                    endDate = getNextDate(2),
//                    time = getCurrentTime()
//                ).catch { exception ->
//                    _mutableState.value = HomeState.Error(exception)
//                }.collect {
//                    _mutableState.value = HomeState.Success(it)
//                }
//            }
//        }
//    }
//}


@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toolsHomeRepo: ToolsHomeRepo,
    private val authRepo: AuthRepo,
) : ViewModel() {

    private val _mutableState = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _mutableState.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            try {
                authRepo.getUser()?.let { data ->
                    toolsHomeRepo.getHomeData(
                        userId = data.uid,
                        latitude = "28.6353",
                        longitude = "77.2250",
                        location = "bangalore",
                        startDate = getCurrentDate(),
                        endDate = getNextDate(2),
                        time = getCurrentTime()
                    ).collect {
                        _mutableState.value = HomeState.Success(it)
                    }
                }
            } catch (networkException: IOException) {
                _mutableState.value = HomeState.NetworkError(networkException)
            } catch (exception: Exception) {
                _mutableState.value = HomeState.Error(exception)
            }
        }
    }
}

package fit.asta.health.tools.sunlight.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.sunlight.model.SunlightToolRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class SunlightViewModel
@Inject constructor(
    private val sunlightToolRepo: SunlightToolRepo,
) : ViewModel() {


    private val mutableState = MutableStateFlow<SunlightState>(SunlightState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadSunlightToolData()
    }

    private fun loadSunlightToolData() {
        viewModelScope.launch {
            sunlightToolRepo.getSunlightTool(userId = "62fcd8c098eb9d5ed038b563")
                .catch { exception ->
                    mutableState.value = SunlightState.Error(exception)
                }.collect {
                    mutableState.value = SunlightState.Success(it)
                }
        }
    }


}
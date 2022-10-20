package fit.asta.health.tools.water.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.water.intent.WaterState
import fit.asta.health.tools.water.model.WaterToolRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class WaterViewModel
@Inject constructor(
    private val waterToolRepo: WaterToolRepo,
) : ViewModel() {

    private val mutableState = MutableStateFlow<WaterState>(WaterState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadWaterToolData()
    }

    private fun loadWaterToolData() {
        viewModelScope.launch {
            waterToolRepo.getWaterTool(userId = "62fcd8c098eb9d5ed038b563").catch { exception ->
                mutableState.value = WaterState.Error(exception)
            }.collect {
                mutableState.value = WaterState.Success(it)
            }
        }
    }
}
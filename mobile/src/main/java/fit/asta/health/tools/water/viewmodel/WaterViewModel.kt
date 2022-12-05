package fit.asta.health.tools.water.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.tools.water.model.WaterToolRepo
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.ModifiedWaterTool
import fit.asta.health.tools.water.model.network.NetBeverage
import fit.asta.health.tools.water.model.network.NetWaterTool
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
    private val authRepo: AuthRepo
) : ViewModel() {

    private val mutableState = MutableStateFlow<WaterState>(WaterState.Loading)
    val state = mutableState.asStateFlow()

    val waterTool = mutableStateOf<NetWaterTool?>(null)

    init {
        loadWaterToolData()
    }

    private fun loadWaterToolData() {
        viewModelScope.launch {
            authRepo.getUser()?.let { user->
                waterToolRepo.getWaterTool(
                    userId = user.uid,
                    latitude = "28.6353",
                    longitude = "77.2250",
                    location = "bangalore",
                    startDate = "2022-12-03",
                    endDate = "2022-12-04",
                ).catch { exception ->
                    mutableState.value = WaterState.Error(exception)
                }.collect {
                    mutableState.value = WaterState.Success
                    waterTool.value = it
                    Log.i("Water Tool",it.toString())
                }
            }

        }
    }

    fun updateWaterTool(modifiedWaterTool: ModifiedWaterTool){
        viewModelScope.launch {
            waterToolRepo.updateWaterTool(modifiedWaterTool).catch { exception->
                mutableState.value = WaterState.Error(exception)
            }.collect{
                if(it.code==200) {
                    mutableState.value = WaterState.Success
                }
            }
        }
    }

    fun updateBeverage(beverage: NetBeverage) {
        viewModelScope.launch {
            waterToolRepo.updateBeverage(beverage).catch { exception ->
                mutableState.value = WaterState.Error(exception)
            }.collect {
                //mutableState.value = WaterState.Success(it)
            }
        }
    }

    fun updateBeverageQty(beverage: NetBeverage) {
        viewModelScope.launch {
            waterToolRepo.updateBeverageQty(beverage).catch { exception ->
                mutableState.value = WaterState.Error(exception)
            }.collect {
                //mutableState.value = WaterState.Success(it)
            }
        }
    }

    fun getBeverageList(userId: String) {
        viewModelScope.launch {
            waterToolRepo.getBeverageList(userId).catch { exception ->
                mutableState.value = WaterState.Error(exception)
            }.collect {
                //mutableState.value = WaterState.Success(it)
            }
        }
    }
}
package fit.asta.health.tools.water.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.auth.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.tools.water.db.WaterData
import fit.asta.health.tools.water.model.WaterLocalRepo
import fit.asta.health.tools.water.model.WaterToolRepo
import fit.asta.health.tools.water.model.domain.BeverageDetails
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.NetBevQtyPut
import fit.asta.health.tools.water.model.network.TodayActivityData
import fit.asta.health.tools.water.model.network.WaterToolData
import fit.asta.health.tools.water.view.screen.WTEvent
import fit.asta.health.tools.water.view.screen.WaterUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class WaterViewModel
@Inject constructor(
    private val waterToolRepo: WaterToolRepo,
    private val authRepo: AuthRepo,
    private val localRepo: WaterLocalRepo
) : ViewModel() {
    val list = listOf(250, 300, 500, 600, 750, 900, 1000)
    private val mutableState = MutableStateFlow<WaterState>(WaterState.Loading)
    val state = mutableState.asStateFlow()

    private val _uiState = mutableStateOf(WaterUiState())
    val uiState: State<WaterUiState> = _uiState

    private val _modifiedWaterTool = MutableStateFlow<WaterTool?>(null)
    val modifiedWaterTool = _modifiedWaterTool.asStateFlow()

    private val _beverageList = mutableStateListOf<BeverageDetails>()
    val beverageList = MutableStateFlow(_beverageList)

    private val _todayActivity = mutableStateListOf<TodayActivityData>()
    val todayActivity = MutableStateFlow(_todayActivity)

    private val _containerList = mutableStateListOf<Int>()
    val containerList = MutableStateFlow(_containerList)

    private val _selectedBeverage = MutableStateFlow<String>("W")
    val selectedBeverage = _selectedBeverage.asStateFlow()

    private val _containerIndex = MutableStateFlow<Int>(-1)
    val containerIndex = _containerIndex.asStateFlow()


    init {
        _containerList.addAll(list)
        loadWaterToolData()
        loadLocalData()
    }

    private fun loadLocalData() {
        viewModelScope.launch {
            val data = localRepo.getWaterData(date = LocalDate.now().dayOfMonth)
            if (data != null) {
                _uiState.value = _uiState.value.copy(
                    start = data.start,
                    angle = data.appliedAngleDistance
                )
            } else {
                localRepo.insert(
                    WaterData(
                        date = LocalDate.now().dayOfMonth,
                        appliedAngleDistance = 0f,
                        start = false
                    )
                )
            }
        }
    }

    fun event(event: WTEvent) {
        when (event) {
            is WTEvent.SelectBeverage -> {
                _selectedBeverage.value = event.Index
            }

            is WTEvent.SelectTarget -> {
                _uiState.value = _uiState.value.copy(target = event.target)
            }

            is WTEvent.SelectAngle -> {
                _uiState.value = _uiState.value.copy(targetAngle = event.angle)
                viewModelScope.launch {
                    val data = localRepo.getWaterData(LocalDate.now().dayOfMonth)
                    if (data != null) {
                        if (!data.start) {
                            localRepo.updateAngle(
                                date = LocalDate.now().dayOfMonth,
                                appliedAngleDistance = event.angle
                            )
                        }
                    } else {
                        localRepo.updateAngle(
                            date = LocalDate.now().dayOfMonth,
                            appliedAngleDistance = event.angle
                        )
                    }
                }
            }

            is WTEvent.SelectContainer -> {
                _containerIndex.value = event.Index
                val title = _beverageList.find { BD ->
                    BD.code == _selectedBeverage.value
                }?.title
                _uiState.value = _uiState.value.copy(
                    showCustomDialog = true,
                    dialogString = "This action cannot be undone. Please review the details carefully before proceeding.\n" +
                            "$title Quantity: ${list[event.Index]} ml\n" +
                            "Have you drunk that much ${title}? Confirming this update will reflect the new quantity in the system."
                )
            }

            is WTEvent.UpdateQuantity -> {
                updateBeverageData()
            }

            is WTEvent.SheetState -> {
                if (event.state) _selectedBeverage.value = "W"
            }

            is WTEvent.Start -> {
                if (_uiState.value.target < 0f) {
                    mToast(context = event.context, text = "select target value of water")
                } else {
                    setTargetData()
                }
            }

            is WTEvent.Schedule -> {}
            is WTEvent.DialogState -> {
                _uiState.value = _uiState.value.copy(showCustomDialog = event.state)
                _containerIndex.value = -1
            }

            else -> {}
        }
    }

    private fun loadWaterToolData() {
        viewModelScope.launch {
            authRepo.getUser()?.let { user ->
                Log.i("User Id", "------------------>${user.uid}")
                Log.d("subhash", "user: ${user.uid}")
                val result = waterToolRepo.getWaterTool(
                    userId = "6309a9379af54f142c65fbfe",
                    latitude = "28.6353",
                    longitude = "77.2250",
                    location = "bangalore",
                    date = getCurrentDate()
                ).catch { exception ->
                    Log.d("subhash", "loadWaterToolData: ${exception.message}")
                    mutableState.value = WaterState.Error(exception)
                }.collect {
                    _modifiedWaterTool.value = it
                    _uiState.value= _uiState.value.copy(
                        butterMilk = it.butterMilk,
                        coconut = it.coconut,
                        fruitJuice = it.fruitJuice,
                        milk = it.milk,
                        water = it.water,
                        meta = it.meta,
                    )
                    _beverageList.addAll(it.beveragesDetails)
                    _todayActivity.addAll(it.todayActivityData)
                    mutableState.value = WaterState.Success
                    Log.i("Water Tool", it.toString())
                    Log.d("subhash", "loadWaterToolData: ${it}")
                }
                Log.d("subhash", "result: ${result}")
            }

        }
    }

    private fun updateUi() {

        viewModelScope.launch {
            authRepo.getUser()?.let { user ->
                val result = waterToolRepo.getWaterTool(
                    userId = "6309a9379af54f142c65fbfe",
                    latitude = "28.6353",
                    longitude = "77.2250",
                    location = "bangalore",
                    date = getCurrentDate()
                ).catch { exception ->
                    Log.d("subhash", "loadWaterToolData: ${exception.message}")
                }.collect {
                    _modifiedWaterTool.value = it
                    _beverageList.clear()
                    _todayActivity.clear()
                    _uiState.value= _uiState.value.copy(
                        butterMilk = it.butterMilk,
                        coconut = it.coconut,
                        fruitJuice = it.fruitJuice,
                        milk = it.milk,
                        water = it.water,
                        meta = it.meta,
                    )
                    _beverageList.addAll(it.beveragesDetails)
                    _todayActivity.addAll(it.todayActivityData)
                }
                Log.d("subhash", "result: ${result}")
            }

        }
    }

    private fun setTargetData() {
        viewModelScope.launch {
            waterToolRepo.updateWaterTool(
                WaterToolData(
                    code = _modifiedWaterTool.value?.waterToolData!!.code,
                    id = _modifiedWaterTool.value?.waterToolData!!.id,
                    prc = _modifiedWaterTool.value?.waterToolData!!.prc,
                    type = _modifiedWaterTool.value?.waterToolData!!.type,
                    weather =_modifiedWaterTool.value?.waterToolData!!.weather ,
                    uid = _modifiedWaterTool.value?.waterToolData!!.uid,
                    waterTarget =_uiState.value.target.toString() ,
                    butterMilkTarget ="0.8",
                    milkTarget ="0.6" ,
                    juiceTarget = "1.0",
                    coconutTarget ="1.5" ,
                )
            ).catch { exception ->
                Log.d("subhash", "setTargetData: ${exception.message}")
            }.collect {
                localRepo.updateState(date = LocalDate.now().dayOfMonth, start = true)
                loadLocalData()
                updateUi()
                Log.d("subhash", "setTargetData: ${it.msg}")
            }
        }
    }

    private fun updateBeverageData() {
        val title = _beverageList.find { BD ->
            BD.code == _selectedBeverage.value
        }?.title
        viewModelScope.launch {
            authRepo.getUserId()?.let {
                waterToolRepo.updateBeverageQty(
                    NetBevQtyPut(
                        bev = title!!,
                        id = "",
                        uid = "6309a9379af54f142c65fbfe",
                        qty = list[_containerIndex.value].toDouble() / 1000
                    )
                ).catch { exception ->
                    Log.d("subhash", "updateBeverageData: ${exception.message}")
                }.collect {
                    updateUi()
                    Log.d("subhash", "updateBeverageData: ${it.msg}")
                }
            }
        }
    }
}

private fun mToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

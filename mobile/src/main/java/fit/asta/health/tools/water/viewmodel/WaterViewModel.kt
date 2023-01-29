package fit.asta.health.tools.water.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.tools.water.model.WaterToolRepo
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.utils.getCurrentDate
import fit.asta.health.utils.getNextDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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

    private val _waterTool = MutableStateFlow<WaterTool?>(null)

    private val _modifiedWaterTool = MutableStateFlow<WaterTool?>(null)
    val modifiedWaterTool = _modifiedWaterTool.asStateFlow()

    private val _choosenBeverageIndexCode = MutableStateFlow<String?>(null)
    val choosenIndexCode = _choosenBeverageIndexCode.asStateFlow()

    val containerInCharge = _choosenBeverageIndexCode.combine(_modifiedWaterTool){ code,modifiedTool->
        code?.let {
            modifiedTool?.beveragesDetails?.find {BD->
                BD.code==it
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),null)

    private val _selectedContainer = MutableStateFlow<String?>(null)
    val selectedContainer = _selectedContainer.asStateFlow()

    //-------------------slider-----------------------//
    private val _showSlider = MutableStateFlow(false)
    val showSlider = _showSlider.asStateFlow()

    private val _sliderIndex = MutableStateFlow<Int?>(null)
    val sliderIndex = _sliderIndex.asStateFlow()

    private val _sliderInitialValue = MutableStateFlow<Float?>(null)
    val sliderInitialValue = _sliderInitialValue.asStateFlow()

    private val _saveData = MutableStateFlow(false)
    val saveData = _saveData.asStateFlow()

    val somethingChanged = MutableStateFlow<Boolean>(false)

    val changedTgt = mutableStateOf<Int?>(null)

    init {
        loadWaterToolData()
    }

    private fun loadWaterToolData() {
        viewModelScope.launch {
            authRepo.getUser()?.let { user->
                Log.i("User Id","------------------>${user.uid}");
                waterToolRepo.getWaterTool(
                    userId = user.uid,
                    latitude = "28.6353",
                    longitude = "77.2250",
                    location = "bangalore",
                    startDate = getCurrentDate(),
                    endDate = getNextDate(1)
                ).catch { exception ->
                    mutableState.value = WaterState.Error(exception)
                }.collect {
                    _waterTool.value = it
                    _modifiedWaterTool.value = it
                    somethingChanged.value=false
                    if(_choosenBeverageIndexCode.value==null){
                        if(_modifiedWaterTool.value?.selectedListId?.isNotEmpty() == true){
                            _choosenBeverageIndexCode.value = _modifiedWaterTool.value?.selectedListId!![0]
                        }
                    }
                    mutableState.value = WaterState.Success
                    Log.i("Water Tool",it.toString())
                }
            }

        }
    }

    fun updateSelectBeveragesTool(beveragesId: String){
        val temp = _modifiedWaterTool.value
        temp?.beveragesDetails?.forEach{
            if(it.beverageId==beveragesId){
                it.isSelected=!it.isSelected
            }
        }
        _modifiedWaterTool.value=temp
    }

    private fun compareModifiedTool(){
        _saveData.value = _selectedContainer.value!=null
    }

    fun updateTheChoosenIndex(code: String){
        _choosenBeverageIndexCode.value = code
        _selectedContainer.value = null
        _showSlider.value=false
        _sliderIndex.value=null
    }

    fun updateSelectedContainer(containerValue: String){
        if(containerValue==_selectedContainer.value){
            _selectedContainer.value=null
        }else{
            _selectedContainer.value=containerValue
        }
        compareModifiedTool()
    }

    fun updateSliderVisibitlity(index: Int,value: Int){
        Log.i("Water Tool viewModel 131",value.toString())
        if(_sliderIndex.value==null){
            _showSlider.value=false
            _sliderIndex.value=index
            _sliderInitialValue.value=value.toFloat()
            _showSlider.value=true
        }else{
            if(_sliderIndex.value==index){
                _sliderIndex.value=null
                _showSlider.value=false
                _sliderInitialValue.value=null
            }else{
                _showSlider.value=false
                _sliderIndex.value=index
                _sliderInitialValue.value=value.toFloat()
                _showSlider.value=true
            }
        }
    }

    fun closeSlider(){
        _showSlider.value=false
        _sliderIndex.value=null
    }

    fun sliderValueChanged(value: Int){
        if(_sliderIndex.value!=null) {
            val temp = _modifiedWaterTool.value
            temp?.beveragesDetails?.forEach {
                if (it.code == _choosenBeverageIndexCode.value) {
                    it.containers[_sliderIndex.value!!] = value
                }
            }
            _modifiedWaterTool.value=temp
        }
    }

    /*fun updateWaterTool(modifiedWaterTool: ModifiedWaterTool){
        viewModelScope.launch {
            waterToolRepo.updateWaterTool(modifiedWaterTool).catch { exception->
                mutableState.value = WaterState.Error(exception)
            }.collect{
                if(it.code==200) {
                    mutableState.value = WaterState.Success
                }
            }
        }
    }*/

    /*fun updateBeverage(beverage: NetBeverage) {
        viewModelScope.launch {
            waterToolRepo.updateBeverage(beverage).catch { exception ->
                mutableState.value = WaterState.Error(exception)
            }.collect {
                //mutableState.value = WaterState.Success(it)
            }
        }
    }*/

    /*fun updateBeverageQty(beverage: NetBeverage) {
        viewModelScope.launch {
            waterToolRepo.updateBeverageQty(beverage).catch { exception ->
                mutableState.value = WaterState.Error(exception)
            }.collect {
                //mutableState.value = WaterState.Success(it)
            }
        }
    }*/

   /* fun getBeverageList(userId: String) {
        viewModelScope.launch {
            waterToolRepo.getBeverageList(userId).catch { exception ->
                mutableState.value = WaterState.Error(exception)
            }.collect {
                //mutableState.value = WaterState.Success(it)
            }
        }
    }*/

    fun changedTarget(x:Int){
        changedTgt.value=x
    }
}
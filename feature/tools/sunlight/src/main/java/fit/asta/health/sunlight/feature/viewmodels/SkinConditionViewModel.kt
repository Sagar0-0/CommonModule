package fit.asta.health.sunlight.feature.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.sunlight.feature.event.SkinConditionEvents
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinColorUtil
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreenCode
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreens
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionState
import fit.asta.health.sunlight.remote.model.SkinConditionBody
import fit.asta.health.sunlight.remote.model.Sup
import fit.asta.health.sunlight.repo.SunlightRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkinConditionViewModel @Inject constructor(
    private val repo: SunlightRepo,
    @UID private val uid: String
) :
    ViewModel() {
    val title = mutableStateOf("")
    val tabs = SkinConditionScreens.getSkinConditionPagerList()
    lateinit var popBackStack: (() -> Unit)

    val isForUpdate = mutableStateOf(false)

    fun onEvent(event: SkinConditionEvents) {
        when (event) {
            is SkinConditionEvents.OnDataUpdate -> {
                conditionUpdateData[event.id] = event.data
            }

            is SkinConditionEvents.OnSkinColor -> {
                getSkinColor()
            }

            is SkinConditionEvents.OnSkinExposure -> {
                getSkinExposureData()
            }

            is SkinConditionEvents.OnSkinType -> {
                getSkinType()
            }

            is SkinConditionEvents.OnSPF -> {
                getSpfScreen()
            }

            is SkinConditionEvents.OnSupplements -> {
                getSupplementPeriod()
            }
        }
    }


    private val _skinExposureState: MutableStateFlow<SkinConditionState> =
        MutableStateFlow(SkinConditionState())
    val skinExposureState: StateFlow<SkinConditionState> = _skinExposureState.asStateFlow()

    private fun getSkinExposureData() {
        if (skinExposureState.value.skinConditionResponse.isNullOrEmpty()) {
            viewModelScope.launch {
                when (val data = repo.getScreenContentList(SkinConditionScreenCode.EXPOSURE_SCREEN)
                    .toUiState()) {
                    UiState.Loading -> {
                        _skinExposureState.emit(_skinExposureState.value.copy(isLoading = true))
                    }

                    is UiState.Success -> {
                        data.data.let {
                            _skinExposureState.emit(
                                _skinExposureState.value.copy(
                                    skinConditionResponse = it,
                                    isLoading = false
                                )
                            )
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }

    private val _skinColorState: MutableStateFlow<SkinConditionState> =
        MutableStateFlow(SkinConditionState())
    val skinColorState: StateFlow<SkinConditionState> = _skinColorState.asStateFlow()

    private fun getSkinColor() {
        viewModelScope.launch {
            when (val data =
                repo.getScreenContentList(SkinConditionScreenCode.SKIN_COLOR_SCREEN).toUiState()) {
                UiState.Loading -> {
                    _skinColorState.emit(_skinColorState.value.copy(isLoading = true))
                }

                is UiState.Success -> {
                    data.data.let {
                        _skinColorState.emit(
                            _skinColorState.value.copy(
                                skinConditionResponse = it,
                                isLoading = false
                            )
                        )
                    }
                }

                else -> {

                }
            }
        }
    }

    //skin color screen
    val skinColors = SkinColorUtil.getSkinColorData()

    private val _spfState: MutableStateFlow<SkinConditionState> =
        MutableStateFlow(SkinConditionState())
    val spfScreenState: StateFlow<SkinConditionState> = _spfState.asStateFlow()

    fun getSpfScreen() {
        viewModelScope.launch {
            when (val data = repo.getScreenContentList(SkinConditionScreenCode.SUNSCREEN_SPF_SCREEN)
                .toUiState()) {
                UiState.Loading -> {
                    _spfState.emit(_spfState.value.copy(isLoading = true))
                }

                is UiState.Success -> {
                    data.data.let {
                        _spfState.emit(
                            _spfState.value.copy(
                                skinConditionResponse = it,
                                isLoading = false
                            )
                        )
                    }
                }

                else -> {

                }
            }
        }
    }


    //medication screen
    val intervalOptions = mutableStateListOf<String>()
    val selectedIntervalOption = mutableStateOf("")
    val selectedDosage = mutableStateOf("")
    val selectedUOM = mutableStateOf("")


    private val _supplementPeriodState: MutableStateFlow<SkinConditionState> =
        MutableStateFlow(SkinConditionState())
    val supplementPeriodState: StateFlow<SkinConditionState> =
        _supplementPeriodState.asStateFlow()

    fun getSupplementPeriod() {
        viewModelScope.launch {
            when (val data =
                repo.getScreenContentList(SkinConditionScreenCode.SUPPLEMENT_PERIOD_LIST)
                    .toUiState()) {
                UiState.Loading -> {
                    _supplementPeriodState.emit(_supplementPeriodState.value.copy(isLoading = true))
                }

                is UiState.Success -> {
                    intervalOptions.clear()
                    data.data.let {
                        _supplementPeriodState.emit(
                            _supplementPeriodState.value.copy(
                                skinConditionResponse = it,
                                isLoading = false
                            )
                        )
                        it.forEach { options ->
                            intervalOptions.add(options.name ?: "-")
                        }
                    }
                    getUomData()
                }

                else -> {
                    getUomData()
                }
            }
        }
    }

    private val _uomState: MutableStateFlow<SkinConditionState> =
        MutableStateFlow(SkinConditionState())
    val uomState: StateFlow<SkinConditionState> = _uomState.asStateFlow()

    fun getUomData() {
        viewModelScope.launch {
            when (val data =
                repo.getScreenContentList(SkinConditionScreenCode.SUPPLEMENT_UNITS).toUiState()) {
                UiState.Loading -> {
                    _uomState.emit(_uomState.value.copy(isLoading = true))
                }

                is UiState.Success -> {
                    data.data.let {
                        _uomState.emit(
                            _uomState.value.copy(
                                skinConditionResponse = it,
                                isLoading = false
                            )
                        )
                    }
                }

                else -> {

                }
            }
        }
    }


    private val _skinTypeState: MutableStateFlow<SkinConditionState> =
        MutableStateFlow(SkinConditionState())
    val skinTypeState: StateFlow<SkinConditionState> = _skinTypeState.asStateFlow()

    fun getSkinType() {
        viewModelScope.launch {
            when (val data =
                repo.getScreenContentList(SkinConditionScreenCode.SKIN_TYPE_SCREEN).toUiState()) {
                UiState.Loading -> {
                    _skinTypeState.emit(_skinTypeState.value.copy(isLoading = true))
                }

                is UiState.Success -> {
                    data.data.let {
                        _skinTypeState.emit(
                            _skinTypeState.value.copy(
                                skinConditionResponse = it,
                                isLoading = false
                            )
                        )
                    }
                }

                else -> {

                }
            }
        }
    }

    val conditionUpdateData = mutableStateListOf<Prc>()
    val supplementData = mutableStateOf<Sup?>(null)
    val id = mutableStateOf<String?>(null)

    private val _updateDataState: MutableStateFlow<UiState<PutResponse>> =
        MutableStateFlow(UiState.Idle)
    val updateDataState: StateFlow<UiState<PutResponse>> = _updateDataState.asStateFlow()

    fun updateSunlightData() {
        _updateDataState.value = UiState.Loading
        viewModelScope.launch {
            when (val data = repo.putSkinConditionData(
                SkinConditionBody(
                    id = id.value,
                    uid = uid,
                    type = 4,
                    prc = conditionUpdateData.filter { it.code.isNotEmpty() }
                        .distinctBy { it.code },
                    code = "sunlight",
                    sup = supplementData.value?.copy(
                        unit = selectedUOM.value,
                        intake = selectedDosage.value.isNotEmpty(),
                        iu = if (selectedDosage.value.isEmpty()) {
                            null
                        } else selectedDosage.value.toInt(),
                        prd = selectedIntervalOption.value
                    )
                )
            ).toUiState()) {
                is UiState.Success -> {
                    _updateDataState.emit(data)
                    popBackStack.invoke()
                }

                else -> {
                    _updateDataState.emit(data)
                }
            }
        }
    }


}
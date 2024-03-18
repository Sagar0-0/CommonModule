package fit.asta.health.feature.water.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UserID
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentDateTime
import fit.asta.health.data.water.local.entity.BevDataDetails
import fit.asta.health.data.water.local.entity.BevQuantityConsumed
import fit.asta.health.data.water.local.entity.ConsumptionHistory
import fit.asta.health.data.water.local.entity.Goal
import fit.asta.health.data.water.local.entity.History
import fit.asta.health.data.water.remote.model.BevQty
import fit.asta.health.data.water.remote.model.BeverageDetailsData
import fit.asta.health.data.water.remote.model.TodayActivityData
import fit.asta.health.data.water.remote.model.WaterDetailsData
import fit.asta.health.data.water.repo.HistoryRepo
import fit.asta.health.data.water.repo.WaterToolRepo
import fit.asta.health.data.water.usecase.mapToWaterTool
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.view.screen.WaterToolUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WaterToolViewModel @Inject constructor(
    private val repo: WaterToolRepo,
    private val authRepo: AuthRepo,
    private val historyRepo: HistoryRepo,
    private val prefManager: PrefManager,
    @UserID private val userID: String
) : ViewModel() {
    private val mutableState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val state = mutableState.asStateFlow()

    private val _uiState = mutableStateOf(WaterToolUiState())
    val uiState: State<WaterToolUiState> = _uiState

    private val _beverageList = mutableStateListOf<BeverageDetailsData>()
    val beverageList = MutableStateFlow(_beverageList)

    private val _todayActivity = mutableStateListOf<TodayActivityData>()
    val todayActivity = MutableStateFlow(_todayActivity)

    private val _bevTitle = mutableStateOf("")
    val bevTitle = MutableStateFlow(_bevTitle)

    private val _bevQuantity = mutableIntStateOf(0)
    val bevQuantity = MutableStateFlow(_bevQuantity)

    private val _goal = MutableStateFlow(10000)

    private var searchedList = listOf<WaterDetailsData>()
    private var isStartingSearch = true

    private var _totalConsumed = MutableStateFlow(0f)

    private var _remainingConsumption = MutableStateFlow(_goal.value)

    private var _recentHistory = MutableStateFlow<List<History>>(listOf())
    val recentHistory = _recentHistory.asStateFlow()

    private var isSearching = mutableStateOf(false)
    var _isLoading = mutableStateOf(false)
        private set

    private var undoConsumedQty = 0.0

    val filteredHistory: MutableStateFlow<List<History>> = MutableStateFlow(emptyList())

    val bevList = mutableStateOf<List<WaterDetailsData>>(listOf())

    private val todayDate = LocalDate.now()

    // Initialization
    init {
        initLocal()
        initRemote()
    }

    // Perform initialization tasks related to local data, if any
    private fun initLocal() {
        loadGoal()
        loadLocalBevDetail()
        loadConsumptionHistoryDetail()

    }

    // Perform initialization tasks related to remote data, if any
    private fun initRemote() {
        loadWaterDetail()
        loadWaterToolData()

    }


    // Searching Methods
    fun searchList(query: String) {
        Log.d("rishi", "searchList called")
        val listToSearch = if (isStartingSearch) {
            bevList.value
        } else {
            searchedList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                bevList.value = searchedList
                isSearching.value = false
                isStartingSearch = true
                return@launch
            } else {
                val results = listToSearch.filter {
                    it.name.contains(query.trim(), ignoreCase = true)
                }
                if (isStartingSearch) {
                    searchedList = bevList.value
                    isStartingSearch = false
                }
                bevList.value = results
                isSearching.value = true
            }
        }
        Log.d("rishi", "bevListSearch : ${bevList.value}")
    }

    fun searchHistory(query: String) {
        viewModelScope.launch {
            historyRepo.getAllHistory().collectLatest {
                _recentHistory.value = it
                val results = if (query.isEmpty()) {
                    // If the query is empty, show the entire list
                    it
                } else {
                    // Otherwise, filter the list based on the query
                    it.filter {
                        it.bevName.contains(query.trim(), ignoreCase = true)
                    }
                }
                // Update the filtered list
                filteredHistory.value = results
            }
        }
    }


    // Local Data Loading and Fetching
    private fun loadLocalBevDetail() {
        Log.d("rishi", "loadLocalDetailCalled")

        viewModelScope.launch {
            val list: StateFlow<List<BevDataDetails>> = historyRepo.getAllLocalBevHistory().stateIn(
                viewModelScope, SharingStarted.Lazily, emptyList()
            )
            list.catch {
                Log.d("rishi", "Error while fetching local Bev detials ${it.message}")
            }
                .collect { list ->
                    Log.d("rishi", "localList : $list")
                    if (list.isNotEmpty()) {
                        val bevDetails = list[0]
                        _uiState.value = _uiState.value.copy(
                            sliderValueWater = bevDetails.waterQuantity.toFloat(),
                            sliderValueCoconut = bevDetails.coconutQuantity.toFloat() ,
                            sliderValueFirstPreference = bevDetails.firstPrefQuantity.toFloat(),
                            sliderValueSecondPreference = bevDetails.secondPrefQuantity.toFloat(),
                            sliderValueRecentAdded = bevDetails.recentAddQuantity.toFloat(),
                        )
                    }
                }
        }

    }

    private fun loadGoal() {
        Log.d("rishi", "loadGoalCalled")
        viewModelScope.launch {
            val list: StateFlow<List<Goal>> = historyRepo.getGoal().stateIn(
                viewModelScope, SharingStarted.Lazily, emptyList()
            )
            list.catch {
                Log.d("rishi", "Error while fetching local Bev detials ${it.message}")
            }.collect { list ->
                    Log.d("rishi", "GoalList : $list")
                    if (list.isNotEmpty()) {
                        val goalData = list[0]
                        _goal.value = goalData.goal
                        _uiState.value = _uiState.value.copy(
                            goal = goalData.goal
                        )
                    }
                }
        }
    }

    private fun loadConsumptionHistoryDetail() {
        Log.d("rishi", "loadLocalDetailCalled")

        viewModelScope.launch {
            val localConsumptionList: StateFlow<List<ConsumptionHistory>> =
                historyRepo.getAllConsumptionHistory(todayDate.toString()).stateIn(
                    viewModelScope, SharingStarted.Lazily, emptyList()
                )
            localConsumptionList.catch {
                Log.d("rishi", "Error while fetching local Bev detials ${it.message}")
            }.collect { list ->
                    Log.d("rishi", "localConsumptionList : $list")
                    if (list.isNotEmpty()) {
                        val bevData = list[0]
                        _goal.value = bevData.goal
                        _totalConsumed.value = bevData.totalConsumed.toFloat()
                        _remainingConsumption.value = bevData.remainingToConsume
                        _uiState.value = _uiState.value.copy(
                            totalConsumed = _totalConsumed.value.toInt(),
                            remainingToConsume = _remainingConsumption.value,
                            goal = bevData.goal
                        )
                    }
                }
        }

    }

    // Remote Data Loading and Fetching
    private fun loadWaterDetail() {
        viewModelScope.launch {
            authRepo.getUserId()?.let {
                when (val result = repo.getWaterData()) {
                    is ResponseState.Success -> {
                        bevList.value = result.data
                    }

                    is ResponseState.ErrorMessage -> {
                        mutableState.value = UiState.ErrorMessage(result.resId)
                    }

                    is ResponseState.ErrorRetry -> {
                        mutableState.value = UiState.ErrorRetry(result.resId)
                    }

                    else -> {
                        UiState.NoInternet
                    }
                }
            }
        }
    }

    private fun loadWaterToolData() {
        mutableState.value = UiState.Loading
        _isLoading.value = true
        viewModelScope.launch {
            authRepo.getUser()?.let {
                prefManager.address.collectLatest { pref ->
                    when (val result = repo.getWaterTool(
                        userId = userID,
                        latitude = pref.lat.toString(),
                        longitude = pref.long.toString(),
                        location = pref.currentAddress,
                        date = getCurrentDateTime()
                    )) {
                        is ResponseState.Success -> {
                            val data = mapToWaterTool(result.data)
                            _beverageList.addAll(data.beveragesDetails)
                            _todayActivity.addAll(data.todayActivityData)
                            _isLoading.value = false
                            mutableState.value = UiState.Success(Unit)
                            Log.i("Water Tool", it.toString())
                            Log.d("rishi", "loadWaterToolData: ${data.beveragesDetails}")
                        }

                        is ResponseState.ErrorMessage -> {
                            mutableState.value = UiState.ErrorMessage(result.resId)
                            delay(1000)
                            _isLoading.value = false
                        }

                        is ResponseState.ErrorRetry -> {
                            mutableState.value = UiState.ErrorRetry(result.resId)
                            delay(1000)
                            _isLoading.value = false
                        }

                        else -> {
                            mutableState.value = UiState.NoInternet
                            delay(1000)
                            _isLoading.value = false
                        }
                    }
                }
            }
        }
    }

    // Local Insert,Update,GET methods

    fun insertRecentAdded(history: History) = viewModelScope.launch {
        historyRepo.insertRecentAdded(history)
    }

    private fun insertBevData(bevDetails: BevDataDetails) = viewModelScope.launch {
        historyRepo.insertBevData(bevDetails)
    }

    private fun insertConsumptionHistory(bevData: ConsumptionHistory) = viewModelScope.launch {
        historyRepo.insertConsumptionData(bevData)
    }

    private fun insertGoal(goal: Goal) = viewModelScope.launch {
        historyRepo.insertGoal(goal)
    }

    private fun getUndoConsumedQty(bevName: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val num: Double = historyRepo.getUndoConsumedQty(bevName)
            undoConsumedQty = num
            _uiState.value = _uiState.value.copy(
                undoBevQty = (undoConsumedQty * 1000.0).toInt(),
            )
        }
    }

    private fun undoConsumption(name: String) = viewModelScope.launch(Dispatchers.Default) {
        val res: Int = historyRepo.undoConsumption(name)
        _totalConsumed.value -= (undoConsumedQty * 1000.0).toFloat()
        _totalConsumed.value = maxOf((0.0).toFloat(), _totalConsumed.value)
        _remainingConsumption.value = _goal.value - _totalConsumed.value.toInt()
        _uiState.value = _uiState.value.copy(
            totalConsumed = _totalConsumed.value.toInt(),
            remainingToConsume = _remainingConsumption.value,
            undoBevQty = (undoConsumedQty * 1000.0).toInt(),
            consumedBevExist = res == 1
        )
        Log.d(
            "rishi",
            "Here Undo Value : ${undoConsumedQty} , TotalConsumed : ${_totalConsumed.value}"
        )
        Log.d("rishi", "Deleted Undo Row : $res")
    }


    // Local Data Update method
    private fun updateBeverageDataLocal() {
        val title = _bevTitle.value
        val quantity = _bevQuantity.intValue
        viewModelScope.launch {
            authRepo.getUserId()?.let {
                historyRepo.insertBevQtyConsumed(
                    BevQuantityConsumed(
                        id = 0, bev = title, uid = userID, qty = quantity.toDouble() / 1000
                    )
                )
                _uiState.value = _uiState.value.copy(
                    recentConsumedBevName = title, recentConsumedBevQty = quantity
                )
                when (val result = repo.updateBeverageQty(
                    BevQty(
                        bev = title, id = "", uid = userID, qty = quantity.toDouble() / 1000
                    )
                )) {
                    is ResponseState.Success -> {
                        if (quantity != 0) {
                            Log.d("rishi", "updateBeverageData: ${result.data.id}")
                        }
                    }

                    is ResponseState.ErrorMessage -> {
                        Log.d("rishi", "updateBeverageDataException: ${result.resId}")
                    }

                    is ResponseState.ErrorRetry -> {
                        Log.d("rishi", "updateBeverageDataException: ${result.resId}")
                    }

                    else -> {
                        mutableState.value = UiState.NoInternet
                    }
                }
            }
        }
    }

    // Remote Data Update method
    private fun updateBeverageDataRemote() {
        viewModelScope.launch {
            val consumedBevList = historyRepo.getConsumedBevList().stateIn(
                viewModelScope, SharingStarted.Lazily, listOf()
            )
        }
        // update to server when api created
    }

    // Event Handler
    fun event(event: WTEvent) {
        when (event) {
            WTEvent.UpdateBevQuantity -> updateBeverageDataLocal()
            is WTEvent.UpdateBevDetails -> {
                Log.d("rishi", "Event UpdateBev called")
                _bevTitle.value = event.title
                _bevQuantity.intValue = event.quantity
                _totalConsumed.value += _bevQuantity.intValue
                _remainingConsumption.value = _goal.value - _totalConsumed.value.toInt()
                _uiState.value = _uiState.value.copy(
                    totalConsumed = _totalConsumed.value.toInt(),
                    remainingToConsume = _remainingConsumption.value
                )
                Log.d(
                    "rishi", "Addition: ${_totalConsumed.value} ${_remainingConsumption.value}"
                )
            }

            is WTEvent.ConsumptionDetails -> {
                Log.d("rishi", "Change in Consumption : ${_totalConsumed.value}")
                insertConsumptionHistory(
                    ConsumptionHistory(
                        todayDate.toString(),
                        _goal.value,
                        _totalConsumed.value.toInt(),
                        _remainingConsumption.value
                    )
                )
            }

            is WTEvent.UpdateOnSliderChangeQuantity -> {
                when (event.bevTitle) {
                    "Water" -> {
//                        _waterQuantity.value = event.sliderValue
                        _uiState.value = _uiState.value.copy(
                            sliderValueWater = event.sliderValue
                        )
                    }

                    "Coconut" -> {
//                        _coconutQuantity.value = event.sliderValue
                        _uiState.value = _uiState.value.copy(
                            sliderValueCoconut = event.sliderValue
                        )
                    }

                    "FirstPref" -> {
//                        _firstPrefQuantity.value = event.sliderValue
                        _uiState.value = _uiState.value.copy(
                            sliderValueFirstPreference = event.sliderValue
                        )
                    }

                    "SecondPref" -> {
//                        _secondPrefQuantity.value = event.sliderValue
                        _uiState.value = _uiState.value.copy(
                            sliderValueSecondPreference = event.sliderValue
                        )
                    }

                    "RecentAdd" -> {
//                        _recentAddedQuantity.value = event.sliderValue
                        _uiState.value = _uiState.value.copy(
                            sliderValueRecentAdded = event.sliderValue
                        )
                    }
                }
                insertBevData(
                    BevDataDetails(
                        0,
                        _uiState.value.sliderValueWater.toInt(),
                        _uiState.value.sliderValueCoconut.toInt(),
                        _uiState.value.sliderValueFirstPreference.toInt(),
                        _uiState.value.sliderValueSecondPreference.toInt(),
                        _uiState.value.sliderValueRecentAdded.toInt()
                    )
                )
            }

            is WTEvent.GoalChange -> {
                _goal.value = event.goal
                _remainingConsumption.value = _goal.value - _totalConsumed.value.toInt()
                _uiState.value = _uiState.value.copy(
                    remainingToConsume = _remainingConsumption.value, goal = event.goal
                )
                insertConsumptionHistory(
                    ConsumptionHistory(
                        todayDate.toString(),
                        _goal.value,
                        _totalConsumed.value.toInt(),
                        _remainingConsumption.value
                    )
                )

            }

            is WTEvent.RetrySection -> {
                loadWaterToolData()
            }

            is WTEvent.UndoConsumption -> {
                getUndoConsumedQty(event.bevName)
                //undoConsumption(event.bevName)
            }

            is WTEvent.DeleteRecentConsumption -> {
                undoConsumption(event.bevName)
            }

            is WTEvent.OnDisposeAddData -> {
                insertGoal(Goal(0, _goal.value))
                insertConsumptionHistory(
                    ConsumptionHistory(
                        todayDate.toString(),
                        _goal.value,
                        _totalConsumed.value.toInt(),
                        _remainingConsumption.value
                    )
                )
                // update when api becomes avaliable
                // updateBeverageDataRemote()
            }

            else -> {}
        }
    }
}


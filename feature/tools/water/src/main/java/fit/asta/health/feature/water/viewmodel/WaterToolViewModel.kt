package fit.asta.health.feature.water.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.data.water.check.model.BevDataDetails
import fit.asta.health.data.water.check.model.ConsumptionHistory
import fit.asta.health.data.water.check.model.Goal
import fit.asta.health.data.water.check.model.History
import fit.asta.health.data.water.model.HistoryRepo
import fit.asta.health.data.water.model.WaterToolRepo
import fit.asta.health.data.water.model.domain.BeverageDetailsData
import fit.asta.health.data.water.model.network.NetBevQtyPut
import fit.asta.health.data.water.model.network.TodayActivityData
import fit.asta.health.data.water.model.network.WaterDetailsData
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.water.WaterState
import fit.asta.health.feature.water.view.screen.WTEvent
import fit.asta.health.feature.water.view.screen.WaterToolUiState
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WaterToolViewModel @Inject constructor(
    private val repo: WaterToolRepo,
    private val authRepo: AuthRepo,
    private val historyRepo: HistoryRepo,
    private val prefManager: PrefManager,
    @UID private val uid: String
) : ViewModel() {
    private val mutableState = MutableStateFlow<WaterState>(WaterState.Loading)
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
    val goal = _goal.asStateFlow()

    private val _waterQuantity = MutableStateFlow(0f)
    val waterQuantity = _waterQuantity.asStateFlow()

    private val _coconutQuantity = MutableStateFlow(0f)
    val coconutQuantity = _coconutQuantity.asStateFlow()

    private val _firstPrefQuantity = MutableStateFlow(0f)
    val firstPrefQuantity = _firstPrefQuantity.asStateFlow()

    private val _secondPrefQuantity = MutableStateFlow(0f)
    val secondPrefQuantity = _secondPrefQuantity.asStateFlow()

    private val _recentAddedQuantity = MutableStateFlow(0f)
    val recentAddedQuantity = _recentAddedQuantity.asStateFlow()

    private var searchedList = listOf<WaterDetailsData>()
    private var isStartingSearch = true

    private var _totalConsumed = MutableStateFlow(0f)
    val totalConsumed = _totalConsumed.asStateFlow()

    private var _remainingConsumption = MutableStateFlow(_goal.value)
    val remainingConsumption = _remainingConsumption.asStateFlow()

    private var _sliderColor = MutableStateFlow(Color.Blue)
    val sliderColor = _sliderColor.asStateFlow()

    private var isSearching = mutableStateOf(false)
    var _isLoading = mutableStateOf(false)
        private set

    val filteredHistory: MutableStateFlow<List<History>> = MutableStateFlow(emptyList())

    val bevList = mutableStateOf<List<WaterDetailsData>>(listOf())

    private val todayDate = LocalDate.now()

    // Initialization
    init {
        loadGoal()
        loadWaterDetail()
        loadLocalBevDetail()
        loadConsumptionHistoryDetail()
        loadWaterToolData()
        Log.d("rishi", "InitsearchList called")
        Log.d("rishi", "bevListInit : ${bevList.value}")
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
            // Collect the recent history list
            recentHistory.collect { historyList ->
                // Filter the list based on the query
                val results = if (query.isEmpty()) {
                    // If the query is empty, show the entire list
                    historyList
                } else {
                    // Otherwise, filter the list based on the query
                    historyList.filter {
                        it.bevName.contains(query.trim(), ignoreCase = true)
                    }
                }
                // Update the filtered list
                filteredHistory.value = results
            }
        }
    }


    // Data Loading and Fetching
    private fun loadLocalBevDetail() {
        Log.d("rishi", "loadLocalDetailCalled")
        val list: StateFlow<List<BevDataDetails>> = historyRepo.getAllLocalBevHistory().stateIn(
            viewModelScope, SharingStarted.Lazily, emptyList()
        )
        viewModelScope.launch {
            list.catch {
                Log.d("rishi", "Error while fetching local Bev detials ${it.message}")
            }
                .collect { list ->
                    Log.d("rishi", "localList : $list")
                    if (list.isNotEmpty()) {
                        val bevDetails = list[0]
                        _waterQuantity.value = bevDetails.waterQuantity.toFloat()
                        _coconutQuantity.value = bevDetails.coconutQuantity.toFloat()
                        _firstPrefQuantity.value = bevDetails.firstPrefQuantity.toFloat()
                        _secondPrefQuantity.value = bevDetails.secondPrefQuantity.toFloat()
                        _recentAddedQuantity.value = bevDetails.recentAddQuantity.toFloat()
                    }
                }
        }

    }

    private fun loadGoal() {
        Log.d("rishi", "loadGoalCalled")
        val list: StateFlow<List<Goal>> = historyRepo.getGoal().stateIn(
            viewModelScope, SharingStarted.Lazily, emptyList()
        )
        viewModelScope.launch {
            list.catch {
                Log.d("rishi", "Error while fetching local Bev detials ${it.message}")
            }
                .collect { list ->
                    Log.d("rishi", "GoalList : $list")
                    if (list.isNotEmpty()) {
                        val goalData = list[0]
                        _goal.value = goalData.goal
                    }
//                    else{
//                        insertGoal(Goal(0,_goal.value.toString()))
//                    }
                }
        }
    }

    private fun loadConsumptionHistoryDetail() {
        Log.d("rishi", "loadLocalDetailCalled")
        val list: StateFlow<List<ConsumptionHistory>> =
            historyRepo.getAllConsumptionHistory(todayDate.toString()).stateIn(
                viewModelScope, SharingStarted.Lazily, emptyList()
            )
        viewModelScope.launch {
            list.catch {
                Log.d("rishi", "Error while fetching local Bev detials ${it.message}")
            }
                .collect { list ->
                    Log.d("rishi", "localConsumptionList : $list")
                    if (list.isNotEmpty()) {
                        val bevData = list[0]
                        _goal.value = bevData.goal
                        _totalConsumed.value = bevData.totalConsumed.toFloat()
                        _remainingConsumption.value = bevData.remainingToConsume
                    }
//                    else{
//                        insertConsumptionHistory(
//                            ConsumptionHistory(date = todayDate.toString(),
//                            goal = _goal.value, totalConsumed = 0, remainingToConsume = 0)
//                        )
//                    }
                }
        }

    }

    private fun loadWaterDetail() {
        Log.d("rishi", "loadWaterDetailCalled")
        viewModelScope.launch {
            authRepo.getUserId()?.let {
                val result = repo.getWaterData()
                    .catch { exception ->
                        Log.e("rishi", "Error in loadWaterDetail() ${exception.message.toString()}")
                        mutableState.value = WaterState.Error("$exception + loadWater")
                    }
                    .collect {
                        bevList.value = it
                        Log.d("rishi", "bevList : $it")
                        //mutableState.value = WaterState.Success
                    }
                Log.d("rishi", result.toString())
            }
        }
    }

    private fun loadWaterToolData() {
        _isLoading.value = true
        viewModelScope.launch {
            authRepo.getUser()?.let { user ->
                prefManager.address.collectLatest { pref ->
//                Log.i("User Id", "------------------>${user.uid}")
                    Log.d("rishi", "user: $uid loc : ${pref.currentAddress}")
                    val result = repo.getWaterTool(
                        userId = uid,
                        latitude = pref.lat.toString(),
                        longitude = pref.long.toString(),
                        location = pref.currentAddress,
                        date = getCurrentDate()
                    ).catch { exception ->
                        Log.d("rishi", "Error in loadWaterToolData: ${exception.message}")
                        mutableState.value = WaterState.Error("$exception + loadWaterTool")
                        delay(1000)
                        _isLoading.value = false
                    }.collect {
                        _beverageList.addAll(it.beveragesDetails)
                        _todayActivity.addAll(it.todayActivityData)
                        mutableState.value = WaterState.Success
                        _isLoading.value = false
                        Log.i("Water Tool", it.toString())
                        Log.d("rishi", "loadWaterToolData: ${it.beveragesDetails}")
                    }
                    Log.d("rishi", "result: $result")
                }
            }
        }
    }
    // Room DB Insert,Update,GET methods

    var recentHistory: StateFlow<List<History>> = historyRepo.getAllHistory().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

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

    private fun updateBeverageData() {
        val title = _bevTitle.value
        val quantity = _bevQuantity.intValue
        viewModelScope.launch {
            authRepo.getUserId()?.let {
                Log.d("rishi", "BevTitle : $title BevQuantity : $quantity")
                repo.updateBeverageQty(
                    NetBevQtyPut(
                        bev = title,
                        id = "",
                        uid = uid,
                        qty = quantity.toDouble() / 1000
                    )
                ).catch { exception ->
                    Log.d("rishi", "updateBeverageDataException: ${exception.message}")
                }.collect {
//
                    Log.d("rishi", "updateBeverageData: ${it.msg}")
                }
            }
        }
    }

    fun event(event: WTEvent) {
        when (event) {
            WTEvent.UpdateBevQuantity -> updateBeverageData()
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
                insertConsumptionHistory(
                    ConsumptionHistory(
                        todayDate.toString(),
                        _goal.value,
                        _totalConsumed.value.toInt(),
                        _remainingConsumption.value
                    )
                )
                Log.d(
                    "rishi",
                    "Addition: ${_totalConsumed.value} ${_remainingConsumption.value}"
                )
            }

            is WTEvent.UpdateOnSliderChangeQuantity -> {
                Log.d(
                    "rishi",
                    "sliderCall : ${_waterQuantity.value} ${_coconutQuantity.value} ${_firstPrefQuantity.value}" +
                            " ${_recentAddedQuantity.value}"
                )
                when (event.bevTitle) {
                    "Water" -> _waterQuantity.value = event.sliderValue
                    "Coconut" -> _coconutQuantity.value = event.sliderValue
                    "FirstPref" -> _firstPrefQuantity.value = event.sliderValue
                    "SecondPref" -> _secondPrefQuantity.value = event.sliderValue
                    "RecentAdd" -> _recentAddedQuantity.value = event.sliderValue
                }
                insertBevData(
                    BevDataDetails(
                        0,
                        _waterQuantity.value.toInt(),
                        _coconutQuantity.value.toInt(),
                        _firstPrefQuantity.value.toInt(),
                        _secondPrefQuantity.value.toInt(),
                        _recentAddedQuantity.value.toInt()
                    )
                )

                Log.d(
                    "rishi",
                    "sliderCall : ${_waterQuantity.value} ${_coconutQuantity.value} ${_firstPrefQuantity.value}" +
                            " ${_recentAddedQuantity.value}"
                )
            }

            is WTEvent.GoalChange -> {
                _goal.value = event.goal
                insertGoal(Goal(0, _goal.value))
                _remainingConsumption.value = _goal.value - _totalConsumed.value.toInt()
                insertConsumptionHistory(
                    ConsumptionHistory(
                        todayDate.toString(),
                        _goal.value,
                        _totalConsumed.value.toInt(),
                        _remainingConsumption.value
                    )
                )

            }

            is WTEvent.colorChange -> {
                _sliderColor.value = event.color
            }

            is WTEvent.RetrySection -> {
                loadWaterToolData()
            }

            else -> {}
        }
    }
}

private fun mToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

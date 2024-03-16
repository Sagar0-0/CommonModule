package fit.asta.health.feature.sleep.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UserID
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getCurrentDateTime
import fit.asta.health.data.sleep.model.SleepLocalRepo
import fit.asta.health.data.sleep.model.SleepRepository
import fit.asta.health.data.sleep.model.db.SleepData
import fit.asta.health.data.sleep.model.network.common.ToolData
import fit.asta.health.data.sleep.model.network.common.Value
import fit.asta.health.data.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.data.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.data.sleep.model.network.goals.SleepGoalData
import fit.asta.health.data.sleep.model.network.jetlag.JetLagTipsData
import fit.asta.health.data.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.data.sleep.model.network.post.Slp
import fit.asta.health.data.sleep.utils.SleepNetworkCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class SleepToolViewModel @Inject constructor(
    private val remoteRepository: SleepRepository,
    private val localRepository: SleepLocalRepo,
    @UserID private val userID: String
) : ViewModel() {

    private var toolDataID = ""

    /**
     * This variable keeps the user UI Default values which we get from the get Request to the
     * server and we need to populate the UI according to this variable
     */
    private val _userUIDefaults = MutableStateFlow<SleepNetworkCall<SleepToolGetResponse>>(
        SleepNetworkCall.Initialized()
    )
    val userUIDefaults = _userUIDefaults.asStateFlow()

    /**
     * This variable contains the Timer Status, Whether the timer is already started or there is
     * some value previously in the local database or not
     */
    private val _timerStatus = MutableStateFlow<SleepNetworkCall<List<SleepData>>>(
        SleepNetworkCall.Initialized()
    )
    val timerStatus = _timerStatus.asStateFlow()


    init {
        getUserData()
    }

    /**
     * This function fetches the data of the default UI element settings of the user
     * from the Server
     */
    fun getUserData() {

        // Starting the Loading State
        _userUIDefaults.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            // Fetching the data from the server
            val response = remoteRepository.getUserDefaultSettings(
                userId = userID,
                date = getCurrentDateTime()
            )

            // Fetching the Local Data of whether the Timer is already started or not
            val localData = localRepository.getData()
            _timerStatus.value = SleepNetworkCall.Success(data = localData)

            when (response) {
                is ResponseState.Success -> {
                    _userUIDefaults.value = SleepNetworkCall.Success(response.data)

                    toolDataID =
                        if (_userUIDefaults.value.data?.sleepData?.toolData!!.uid != "000000000000000000000000")
                            _userUIDefaults.value.data?.sleepData?.toolData!!.id
                        else
                            ""
                }

                is ResponseState.ErrorMessage -> {
                    _userUIDefaults.value =
                        SleepNetworkCall.Failure(message = "No Data Present")
                }

                is ResponseState.ErrorRetry -> {
                    _userUIDefaults.value =
                        SleepNetworkCall.Failure(message = "No Data Present")
                }

                is ResponseState.NoInternet -> {
                    _userUIDefaults.value =
                        SleepNetworkCall.Failure(message = "No Internet Present")
                }
            }
        }
    }

    /**
     * This function keeps the api call state when the user updates any UI element and puts the new
     * refreshed data to the Server
     */
    private val _userValueUpdate = MutableStateFlow<SleepNetworkCall<Unit>>(
        SleepNetworkCall.Initialized()
    )
    val userValueUpdate = _userValueUpdate.asStateFlow()

    /**
     * This function puts the data of the UI to the Server and sets the default settings of the user
     */
    private fun updateUserData() {

        _userValueUpdate.value = SleepNetworkCall.Loading()

        viewModelScope.launch {


            val response = remoteRepository.putUserData(
                toolData = ToolData(
                    code = "sleep",
                    id = toolDataID,
                    prc = _userUIDefaults.value.data?.sleepData?.toolData?.prc!!,
                    type = 1,
                    uid = userID
                )
            )

            _userValueUpdate.value = when (response) {
                is ResponseState.Success -> {
                    if (toolDataID == "")
                        toolDataID = response.data.id
                    SleepNetworkCall.Success(data = Unit)
                }

                is ResponseState.ErrorMessage -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.ErrorRetry -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.NoInternet -> {
                    SleepNetworkCall.Failure(message = "No Internet Present")
                }
            }
        }
    }


    /**
     * This variable contains the Sleep Disturbances UI data
     */
    private val _sleepDisturbancesData =
        MutableStateFlow<SleepNetworkCall<SleepDisturbanceResponse>>(SleepNetworkCall.Initialized())
    val sleepDisturbancesData = _sleepDisturbancesData.asStateFlow()

    /**
     * This function fetches the Sleep Disturbances data from the Server which is then shown to the
     * UI
     */
    fun getDisturbancesData() {

        // Setting the Loading State
        _sleepDisturbancesData.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            // Fetching the Data from the server
            val response = remoteRepository.getPropertyData(
                userId = userID,
                property = "sd"
            )
            _sleepDisturbancesData.value = when (response) {
                is ResponseState.Success -> {
                    SleepNetworkCall.Success(data = response.data)
                }

                is ResponseState.ErrorMessage -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.ErrorRetry -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.NoInternet -> {
                    SleepNetworkCall.Failure(message = "No Internet Present")
                }
            }

        }
    }


    /**
     * This variable contains the Sleep Factors UI data
     */
    private val _sleepFactorsData =
        MutableStateFlow<SleepNetworkCall<SleepDisturbanceResponse>>(SleepNetworkCall.Initialized())
    val sleepFactorsData = _sleepFactorsData.asStateFlow()

    /**
     * This function fetches the Sleep Factors data from the Server which is then shown to the
     * UI
     */
    fun getSleepFactorsData() {

        // Setting the Loading State
        _sleepFactorsData.value = SleepNetworkCall.Loading()

        viewModelScope.launch {

            // Fetching the Data from the server
            val response = remoteRepository.getPropertyData(
                userId = userID,
                property = "sf"
            )
            _sleepFactorsData.value = when (response) {
                is ResponseState.Success -> {
                    SleepNetworkCall.Success(data = response.data)
                }

                is ResponseState.ErrorMessage -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.ErrorRetry -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.NoInternet -> {
                    SleepNetworkCall.Failure(message = "No Internet Present")
                }
            }
        }
    }

    /**
     * This variable contains the goals Options List which is provided by the Server
     */
    private val _goalsList = MutableStateFlow<SleepNetworkCall<List<SleepGoalData>>>(
        SleepNetworkCall.Initialized()
    )
    val goalsList = _goalsList.asStateFlow()

    /**
     * This function fetches the Goals List from the Server
     */
    fun getGoalsList() {
        _goalsList.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            val response = remoteRepository.getGoalsList(property = "goal")

            _goalsList.value = when (response) {
                is ResponseState.Success -> {
                    SleepNetworkCall.Success(data = response.data)
                }

                is ResponseState.ErrorMessage -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.ErrorRetry -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.NoInternet -> {
                    SleepNetworkCall.Failure(message = "No Internet Present")
                }
            }

        }
    }

    private val _jetLagDetails = MutableStateFlow<SleepNetworkCall<JetLagTipsData>>(
        SleepNetworkCall.Initialized()
    )
    val jetLagDetails = _jetLagDetails.asStateFlow()

    /**
     * This function fetches the jet lag Tips from the server
     */
    fun getJetLagTips() {

        // Setting the Loading State
        _jetLagDetails.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            // Fetching the Data from the server
            val response = remoteRepository.getJetLagTips(id = "64b12f149fd4fae964059446")

            _jetLagDetails.value = when (response) {
                is ResponseState.Success -> {
                    SleepNetworkCall.Success(data = response.data)
                }

                is ResponseState.ErrorMessage -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.ErrorRetry -> {
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
                }

                is ResponseState.NoInternet -> {
                    SleepNetworkCall.Failure(message = "No Internet Present")
                }
            }
        }
    }


    fun updateToolData(toolType: String, newValue: String) {
        val prc = _userUIDefaults.value.data?.sleepData?.toolData?.prc?.find { it.ttl == toolType }
        val value = prc?.values?.find { it.value == newValue }
        if (value == null) {
            prc?.values?.add(
                Value(
                    id = "",
                    name = newValue,
                    value = newValue
                )
            )
        } else
            prc.values!!.remove(value)

        updateUserData()
    }

    fun setTimerStatus() {

        // Setting the Loading State
        _userValueUpdate.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            // Checking if the Timer Status Data is null or not
            if (_timerStatus.value.data.isNullOrEmpty()) {

                // Inserting the sleep Data in the Local Storage
                localRepository.insert(
                    data = SleepData(
                        key = 0,
                        startTime = LocalDateTime.now()
                    )
                )

                // Fetching the Local Data
                val data = localRepository.getData()
                _timerStatus.value = SleepNetworkCall.Success(data = data)
                _userValueUpdate.value = SleepNetworkCall.Success(data = Unit)
            } else {

                // Current Time and Start Time
                val currentTime = LocalDateTime.now()
                val startTime = _timerStatus.value.data!!.first().startTime

                // Calculating the Hours for which the user was sleeping
                val hours = startTime.until(currentTime, ChronoUnit.MINUTES) / 60f

                // Posting the Reading to the Server
                val response = remoteRepository.postUserReading(
                    userId = userID,
                    sleepPostRequestBody = SleepPostRequestBody(
                        id = "",
                        uid = userID,
                        dur = hours.toDouble(),
                        reg = 1.0,
                        slp = Slp(
                            deep = 1,
                            nor = 1,
                            dly = 1,
                            dis = 1
                        )
                    )
                )

                _userValueUpdate.value = when (response) {
                    is ResponseState.Success -> {
                        localRepository.deleteData()
                        SleepNetworkCall.Success(data = Unit)
                    }

                    is ResponseState.ErrorMessage -> {
                        SleepNetworkCall.Failure(message = "Unsuccessful operation")
                    }

                    is ResponseState.ErrorRetry -> {
                        SleepNetworkCall.Failure(message = "Unsuccessful operation")
                    }

                    is ResponseState.NoInternet -> {
                        SleepNetworkCall.Failure(message = "No Internet Present")
                    }
                }
                // Fetching the Local Data
                val data = localRepository.getData()
                _timerStatus.value = SleepNetworkCall.Success(data = data)
            }
        }
    }
}
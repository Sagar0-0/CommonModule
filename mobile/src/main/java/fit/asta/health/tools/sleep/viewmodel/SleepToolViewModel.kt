package fit.asta.health.tools.sleep.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.sleep.model.SleepRepository
import fit.asta.health.tools.sleep.model.network.common.ToolData
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.jetlag.SleepJetLagTipResponse
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import fit.asta.health.tools.sleep.utils.SleepNetworkCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SleepToolViewModel @Inject constructor(
    private val remoteRepository: SleepRepository
) : ViewModel() {

    // This contains the user ID for the Api Calls
    private var userIdFromHomeScreen = ""
    private var userIdFromGetResponse = ""

    /**
     * This function sets the user ID inside the viewModel
     */
    fun setUserId(newUserId: String) {
        userIdFromHomeScreen = newUserId
    }

    /**
     * This variable keeps the user UI Default values which we get from the get Request to the
     * server and we need to populate the UI according to this variable
     */
    private val _userUIDefaults = MutableStateFlow<SleepNetworkCall<SleepToolGetResponse>>(
        SleepNetworkCall.Initialized()
    )
    val userUIDefaults = _userUIDefaults.asStateFlow()

    /**
     * This variable contains the user's Tool data from the Server and keeps it cuz it can be changed
     * with time
     */
    private val _userToolsData = MutableStateFlow<ToolData?>(null)
    val userToolsData = _userToolsData.asStateFlow()

    /**
     * This function fetches the data of the default UI element settings of the user
     * from the Server
     */
    fun getUserData() {

        // Starting the Loading State
        _userUIDefaults.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            try {

                // Local Date time
                val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                // Fetching the data from the server
                val response = remoteRepository.getUserDefaultSettings(
                    userId = userIdFromHomeScreen,
                    date = currentDate
                )

                // handling the Response
                if (response.isSuccessful) {
                    _userUIDefaults.value = SleepNetworkCall.Success(data = response.body()!!)
                    _userToolsData.value = _userUIDefaults.value.data?.sleepData?.toolData
                    userIdFromGetResponse = _userUIDefaults.value.data?.sleepData?.toolData!!.uid
                } else
                    _userUIDefaults.value = SleepNetworkCall.Failure(message = "No Data Present")

            } catch (e: Exception) {
                _userUIDefaults.value = SleepNetworkCall.Failure(message = e.message.toString())
            }
        }
    }

    /**
     * This function keeps the api call state when the user updates any UI element and puts the new
     * refreshed data to the Server
     */
    private val _userValueUpdate = MutableStateFlow<SleepNetworkCall<SleepPutResponse>>(
        SleepNetworkCall.Initialized()
    )
    val userValueUpdate = _userValueUpdate.asStateFlow()

    /**
     * This function puts the data of the UI to the Server and sets the default settings of the user
     */
    fun updateUserData() {

        _userUIDefaults.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            _userValueUpdate.value = try {
                val id =
                    if (userIdFromGetResponse == "000000000000000000000000")
                        "" else userIdFromGetResponse

                val response = remoteRepository.putUserData(
                    toolData = ToolData(
                        id = id,
                        uid = userIdFromGetResponse,
                        type = 1,
                        code = "sleep",
                        prc = _userToolsData.value!!.prc
                    )
                )

                if (response.isSuccessful)
                    SleepNetworkCall.Success(data = response.body()!!)
                else
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
            } catch (e: Exception) {
                SleepNetworkCall.Failure(message = e.message.toString())
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
            _sleepDisturbancesData.value = try {

                // Fetching the Data from the server
                val response = remoteRepository.getPropertyData(
                    userId = userIdFromHomeScreen,
                    property = "sd"
                )

                // Handling the Response
                if (response.isSuccessful)
                    SleepNetworkCall.Success(data = response.body()!!)
                else
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
            } catch (e: Exception) {
                SleepNetworkCall.Failure(message = e.message.toString())
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
            _sleepFactorsData.value = try {

                // Fetching the Data from the server
                val response = remoteRepository.getPropertyData(
                    userId = userIdFromHomeScreen,
                    property = "sf"
                )

                // Handling the Response
                if (response.isSuccessful)
                    SleepNetworkCall.Success(data = response.body()!!)
                else
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
            } catch (e: Exception) {
                SleepNetworkCall.Failure(message = e.message.toString())
            }
        }
    }

    /**
     * This variable contains the selected sleep disturbances of the User and stores them for later
     * use
     */
    private val _selectedSleepDisturbances =
        MutableStateFlow(mutableListOf("Dream", "Kids", "Love"))
    val selectedSleepDisturbances = _selectedSleepDisturbances.asStateFlow()

    /**
     * This variable contains the goals Options List which is provided by the Server
     */
    private val _goalsOptionList = MutableStateFlow(
        mutableListOf("De-Stress", "Fall Asleep", "Take a Break", "Clear Your Mind")
    )
    val goalsOptionList = _goalsOptionList.asStateFlow()

    /**
     * This stores the user's currently Selected Goal
     */
    var currentSelectedGoal = "De - Stress"
        private set


    private val _jetLagDetails = MutableStateFlow<SleepNetworkCall<SleepJetLagTipResponse>>(
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
            _jetLagDetails.value = try {

                // Fetching the Data from the server
                val response = remoteRepository.getJetLagTips(id = "64b12f149fd4fae964059446")

                // Handling the Response
                if (response.isSuccessful)
                    SleepNetworkCall.Success(data = response.body()!!)
                else
                    SleepNetworkCall.Failure(message = "Unsuccessful operation")
            } catch (e: Exception) {
                SleepNetworkCall.Failure(message = e.message.toString())
            }
        }
    }


    fun updateSleepDisturbances(selectedDisturbance: String) {
        if (_selectedSleepDisturbances.value.contains(selectedDisturbance))
            _selectedSleepDisturbances.value.remove(selectedDisturbance)
        else
            _selectedSleepDisturbances.value.add(selectedDisturbance)
    }
}
package fit.asta.health.tools.sleep.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.sleep.model.SleepLocalRepo
import fit.asta.health.tools.sleep.model.SleepRepository
import fit.asta.health.tools.sleep.model.network.common.ToolData
import fit.asta.health.tools.sleep.model.network.common.Value
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.goals.SleepGoalResponse
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
    private val remoteRepository: SleepRepository,
    private val localRepository: SleepLocalRepo
) : ViewModel() {

    // This contains the user ID for the Api Calls
    private var userIdFromHomeScreen = ""
    private var toolDataID = ""

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

                    toolDataID =
                        if (_userUIDefaults.value.data?.sleepData?.toolData!!.uid != "000000000000000000000000")
                            _userUIDefaults.value.data?.sleepData?.toolData!!.id
                        else
                            ""
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
    private fun updateUserData() {

        _userValueUpdate.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            _userValueUpdate.value = try {

                val response = remoteRepository.putUserData(
                    toolData = ToolData(
                        code = "sleep",
                        id = toolDataID,
                        prc = _userUIDefaults.value.data?.sleepData?.toolData?.prc!!,
                        type = 1,
                        uid = userIdFromHomeScreen
                    )
                )

                if (response.isSuccessful) {
                    if (toolDataID == "")
                        toolDataID = response.body()!!.data.id

                    SleepNetworkCall.Success(data = response.body()!!)
                } else
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
     * This variable contains the goals Options List which is provided by the Server
     */
    private val _goalsList = MutableStateFlow<SleepNetworkCall<SleepGoalResponse>>(
        SleepNetworkCall.Initialized()
    )
    val goalsList = _goalsList.asStateFlow()

    /**
     * This function fetches the Goals List from the Server
     */
    fun getGoalsList() {
        _goalsList.value = SleepNetworkCall.Loading()

        viewModelScope.launch {
            _goalsList.value = try {
                val response = remoteRepository.getGoalsList(property = "goal")

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
            prc.values.remove(value)

        updateUserData()
    }
}
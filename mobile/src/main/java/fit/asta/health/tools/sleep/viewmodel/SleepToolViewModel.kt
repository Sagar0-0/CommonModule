package fit.asta.health.tools.sleep.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.sleep.model.SleepRepository
import fit.asta.health.tools.sleep.model.network.common.Prc
import fit.asta.health.tools.sleep.model.network.common.Value
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.put.SleepPutRequestBody
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

    private val prcList = listOf(
        Prc(
            id = "",
            ttl = "goal",
            dsc = "goal",
            values = listOf(
                Value(
                    id = "",
                    name = "De-Stress",
                    value = "De-Stress"
                )
            ),
            type = 1,
            code = "goal"
        ),
        Prc(
            id = "",
            ttl = "factors",
            dsc = "factors",
            values = listOf(
                Value(
                    id = "",
                    name = "Sleep Factors",
                    value = "Sleep Factors"
                )
            ),
            type = 1,
            code = "factors"
        ),
        Prc(
            id = "",
            ttl = "Jet Lag",
            dsc = "Jet Lag",
            values = listOf(
                Value(
                    id = "",
                    name = "Check Tips",
                    value = "Check Tips"
                )
            ),
            type = 1,
            code = "Jet Lag"
        ),
        Prc(
            id = "",
            ttl = "target",
            dsc = "target",
            values = listOf(
                Value(
                    id = "",
                    name = "15",
                    value = "15"
                )
            ),
            type = 1,
            code = "target"
        ),
        Prc(
            id = "",
            ttl = "start time",
            dsc = "start time",
            values = listOf(
                Value(
                    id = "",
                    name = "10:00PM",
                    value = "10:00PM"
                )
            ),
            type = 1,
            code = "start time"
        ),
        Prc(
            id = "",
            ttl = "end time",
            dsc = "end time",
            values = listOf(
                Value(
                    id = "",
                    name = "07:00AM",
                    value = "07:00AM"
                )
            ),
            type = 1,
            code = "end time"
        ),
    )

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
                    sleepPutRequestBody = SleepPutRequestBody(
                        id = id,
                        uid = userIdFromGetResponse,
                        type = 1,
                        code = "sleep",
                        prc = prcList
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
}
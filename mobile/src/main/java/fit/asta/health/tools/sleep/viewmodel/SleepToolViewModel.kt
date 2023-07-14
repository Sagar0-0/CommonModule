package fit.asta.health.tools.sleep.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.sleep.model.LocalRepo
import fit.asta.health.tools.sleep.model.SleepRepository
import fit.asta.health.tools.sleep.model.db.SleepingLocalData
import fit.asta.health.tools.sleep.model.network.common.Prc
import fit.asta.health.tools.sleep.model.network.common.Value
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.put.SleepPutRequestBody
import fit.asta.health.tools.sleep.utils.SleepNetworkCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepToolViewModel @Inject constructor(
    private val remoteRepository: SleepRepository,
    private val localRepo: LocalRepo
) : ViewModel() {

    var userId = ""
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

    private val _userUIDefaults = MutableStateFlow<SleepNetworkCall<SleepToolGetResponse>>(
        SleepNetworkCall.Initialized()
    )
    val userUIDefaults = _userUIDefaults.asStateFlow()


    /**
     * This function fetches the UI related default data of the user
     *
     *          Note :-
     *              1. Checks if there is a userId in the local Database named "sleep-data"
     *
     *              2. If user present then it take that userId and fetches the default settings from the Server
     *
     *              3. If user not present then we make a put request which gives us a new userId for the user
     *              which we take and then we add it in the local database and we user it to make a
     *              fetch request to the Server
     */
    fun fetchUserUIData() {

        _userUIDefaults.value = SleepNetworkCall.Loading()
        viewModelScope.launch {

            // Fetching the Id if present at the Local Storage
            val localResponse = localRepo.getUserId()

            // If the Local Storage is empty and doesn't have userId we do this
            if (localResponse.isEmpty()) {

                // Fetching the Data from the Server
                val remoteResponse = remoteRepository.putUserDataForFirstTimeUsers(
                    sleepPutRequestBody = SleepPutRequestBody(
                        id = "",
                        uid = "",
                        type = 1,
                        code = "sleep",
                        prc = prcList
                    )
                )

                // Setting the UserID and inserting it into the local storage
                if (remoteResponse.isSuccessful) {
                    userId = remoteResponse.body()!!.data.id
                    val data = SleepingLocalData(userId = userId)
                    localRepo.insertUserId(data)
                }
            } else
                userId = localResponse.first().userId

            // Fetching the Data from the server
            getUserDefaultSettings()
        }
    }

    /**
     * This function fetches the data of the default UI element settings of the user
     * from the Server
     */
    private suspend fun getUserDefaultSettings() {
        _userUIDefaults.value = try {

            val response = remoteRepository.getUserDefaultSettings(
                userId,
                date = "2023-07-14"
            )

            if (response.isSuccessful)
                SleepNetworkCall.Success(data = response.body()!!)
            else
                SleepNetworkCall.Failure(message = "No Data Present")

        } catch (e: Exception) {
            SleepNetworkCall.Failure(message = e.message.toString())
        }
    }
}
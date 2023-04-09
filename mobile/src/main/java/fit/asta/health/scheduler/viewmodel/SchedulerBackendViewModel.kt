package fit.asta.health.scheduler.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.scheduler.compose.screen.UiState
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.db.AlarmRepository
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.scheduler.model.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.scheduler.model.net.tag.AstaGetTagsListResponse
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.TAG
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SchedulerBackendViewModel @Inject constructor(
    private val repository: AlarmRepository,
    private val backendRepo:AlarmBackendRepo,
    private val savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    lateinit var state: StateFlow<UiState>

    init {
//        viewModelScope.launch {
//            state = savedStateHandle.getStateFlow(loginScreenStateKey, UiState()).stateIn(
//                scope = viewModelScope
//            )
//        }
    }
    val lisOfSchedules: MutableLiveData<NetworkResult<AstaSchedulerGetListResponse>> =
        MutableLiveData()

    fun getListOfSchedules(userId: String) = viewModelScope.launch {
        getListOfSchedulesSafeCall(userId)
    }

    private suspend fun getListOfSchedulesSafeCall(userId: String) {
        lisOfSchedules.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getScheduleListDataFromBackend(userId)
            lisOfSchedules.value = handleResponse(response)
        } catch (e: Exception) {
            Log.d(TAG, "getListOfSchedulesSafeCall: $e")
            lisOfSchedules.value = NetworkResult.Error(message = e.message)
        }
    }

    val sendSchedulerResponse: MutableLiveData<NetworkResult<AstaSchedulerPutResponse>> =
        MutableLiveData()

    fun sendScheduler(schedule: AlarmEntity) = viewModelScope.launch {
        sendSchedulerSafeCall(schedule)
    }

    private suspend fun sendSchedulerSafeCall(schedule: AlarmEntity) {
        sendSchedulerResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.updateScheduleDataOnBackend(schedule)
            Log.d(TAG, "sendSchedulerSafeCall: ${response.errorBody()?.string()}")
            sendSchedulerResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.d(TAG, "sendSchedulerSafeCall: $e")
            sendSchedulerResponse.value = NetworkResult.Error(message = e.message)
        }
    }

    val scheduleResponse: MutableLiveData<NetworkResult<AstaSchedulerGetResponse>> =
        MutableLiveData()

    fun getScheduleDetails(scheduleId: String) = viewModelScope.launch {
        getScheduleDetailsSafeCall(scheduleId)
    }

    private suspend fun getScheduleDetailsSafeCall(scheduleId: String) {
        scheduleResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getScheduleDataFromBackend(scheduleId)
            scheduleResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.d(TAG, "getScheduleDetailsSafeCall: $e")
            scheduleResponse.value = NetworkResult.Error(message = e.message)
        }
    }

    val deleteScheduleResponse: MutableLiveData<NetworkResult<AstaSchedulerDeleteResponse>> =
        MutableLiveData()

    fun deleteSchedule(scheduleId: String) = viewModelScope.launch {
        deleteScheduleSafeCall(scheduleId)
    }

    private suspend fun deleteScheduleSafeCall(scheduleId: String) {
        deleteScheduleResponse.value = NetworkResult.Loading()

        try {
            val response = repository.remote.deleteScheduleDataFromBackend(scheduleId)
            deleteScheduleResponse.value = handleResponse(response)
        } catch (e: Exception) {
            Log.d(TAG, "deleteScheduleSafeCall: $e")
            deleteScheduleResponse.value = NetworkResult.Error(message = e.message)
        }
    }

    val listOfTags: MutableLiveData<NetworkResult<AstaGetTagsListResponse>> = MutableLiveData()

    fun getTagsListFromBackend(userId: String) = viewModelScope.launch {
        getTagsListFromBackendSafeCall(userId)
    }

    private suspend fun getTagsListFromBackendSafeCall(userId: String) {
        listOfTags.value = NetworkResult.Loading()

        try {
            val response = repository.remote.getTagListFromBackend(userId)
            listOfTags.value = handleResponse(response)
        } catch (e: Exception) {
            Log.d(TAG, "getTagsListFromBackendSafeCall: $e")
            listOfTags.value = NetworkResult.Error(message = e.message)
        }
    }

    // Handle Response Got From Web API
    private fun <T : Any> handleResponse(response: Response<T>): NetworkResult<T> {
        Log.d(SpotifyConstants.TAG, "handleResponse: ${response.body()} ")
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Timeout!!\n $response"
                )
            }

            response.code() == 401 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Bad or expired token. This can happen if the user revoked a token or the access token has expired. You should re-authenticate the user.\n $response"
                )
            }

            response.code() == 403 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Bad OAuth request (wrong consumer key, bad nonce, expired timestamp...). Unfortunately, re-authenticating the user won't help here.\n $response"
                )
            }

            response.code() == 429 -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "The app has exceeded its rate limits. $response"
                )
            }

            response.body() == null -> {
                return NetworkResult.Error(
                    data = response.body(),
                    message = "Empty Body. $response"
                )
            }

            response.isSuccessful -> {
                val result = response.body()
                return NetworkResult.Success(result!!)
            }

            response.code() == 200 -> {
                val result = response.body()
                return NetworkResult.Success(result!!)
            }

            else -> return NetworkResult.Error(
                message = response.body().toString(),
                data = response.body()
            )
        }
    }
    companion object {
        const val loginScreenStateKey = "loginScreenState"
    }
}
package fit.asta.health.navigation.track.viewmodel

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.navigation.track.TrackingOptions
import kotlinx.coroutines.launch
import fit.asta.health.navigation.track.model.TrackingBreathingRepo
import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class TrackViewModel @Inject constructor(
    private val repository: TrackingBreathingRepo
) : ViewModel() {

    private val data = MutableStateFlow<NetBreathingRes?>(null)
    val state = data.asStateFlow()

    private lateinit var currentSelectedTrackingOption: TrackingOptions

    fun changeTrackingOption(newOption: TrackingOptions) {
        currentSelectedTrackingOption = newOption

        /* TODO :-
            1. Make the repository object
         */
    }

    fun getDailyData() {
        viewModelScope.launch {
            repository.getDailyData(
                uid = "6309a9379af54f142c65fbfe",
                date = "2023-June-11",
                location = "bangalore"
            ).catch { exception ->
                d("View Model", exception.toString())
            }.collect {
                data.value = it
                d("View Model", data.value.toString())
            }
        }
    }

}
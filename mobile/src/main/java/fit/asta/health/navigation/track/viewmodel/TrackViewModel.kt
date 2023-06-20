package fit.asta.health.navigation.track.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.navigation.track.TrackingOptions
import fit.asta.health.navigation.track.model.TrackingRepoImpl
import kotlinx.coroutines.launch
import fit.asta.health.BuildConfig.BASE_URL
import okhttp3.OkHttpClient


class TrackViewModel : ViewModel() {

    val repository = TrackingRepoImpl(
        baseUrl = BASE_URL,
        client = OkHttpClient()
    )


    lateinit var currentSelectedTrackingOption: TrackingOptions
        private set

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
            )
        }
    }

}
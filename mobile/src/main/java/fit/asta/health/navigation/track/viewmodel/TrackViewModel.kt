package fit.asta.health.navigation.track.viewmodel

import androidx.lifecycle.ViewModel
import fit.asta.health.navigation.track.TrackingOptions

class TrackViewModel : ViewModel() {

    lateinit var currentSelectedTrackingOption: TrackingOptions
        private set

    fun changeTrackingOption(newOption: TrackingOptions) {
        currentSelectedTrackingOption = newOption
    }

}
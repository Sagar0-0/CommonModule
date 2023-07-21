package fit.asta.health.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.PrefUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val prefUtils: PrefUtils
) : ViewModel() {

    private val _locationName = MutableStateFlow("")
    val locationName = _locationName.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled = _notificationsEnabled.asStateFlow()


    init {
        _notificationsEnabled.value = prefUtils.getMasterNotification()
        _locationName.value = prefUtils.getCurrentAddress()
    }

    fun setNotificationStatus(newValue: Boolean) {
        _notificationsEnabled.value = newValue
        prefUtils.setMasterNotification(newValue)
    }

    fun setCurrentLocation(newValue: String) {
        _locationName.value = newValue
        prefUtils.setCurrentAddress(newValue)
    }
}
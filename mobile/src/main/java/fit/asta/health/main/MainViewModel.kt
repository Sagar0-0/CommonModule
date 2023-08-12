package fit.asta.health.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.common.utils.PrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val prefManager: PrefManager
) : ViewModel() {

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled = _notificationsEnabled.asStateFlow()


    init {
        viewModelScope.launch {
//            prefManager.getPreferences(
//                R.string.user_pref_notification_key,
//                true
//            ).collect {
//                _notificationsEnabled.value = it
//                Log.d("INIT", "init: $it")
//            } TODO
        }
    }

    fun setNotificationStatus(newValue: Boolean) = viewModelScope.launch {
//        prefManager.setPreferences(
//            R.string.user_pref_notification_key,
//            newValue
//        ) TODO
    }
}
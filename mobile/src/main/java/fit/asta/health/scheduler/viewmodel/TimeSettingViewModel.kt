package fit.asta.health.scheduler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.model.net.scheduler.Rep
import javax.inject.Inject

@HiltViewModel
class TimeSettingViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    // snooze
    private val mutableSnoozeTime = MutableLiveData<Int>()
    val snoozeTime: LiveData<Int> get() = mutableSnoozeTime

    fun setSnoozeInViewModel(item: Int) {
        mutableSnoozeTime.value = item
    }

    private val mutableIsSnoozeBottomSheetVisible = MutableLiveData<Boolean>()
    val isSnoozeBottomSheetVisible: LiveData<Boolean> get() = mutableIsSnoozeBottomSheetVisible

    fun setIsSnoozeBottomSheetVisible(item: Boolean) {
        mutableIsSnoozeBottomSheetVisible.value = item
    }

    // advanced duration
    private val mutableAdvancedDuration = MutableLiveData<Int>()
    val advancedDuration: LiveData<Int> get() = mutableAdvancedDuration

    fun setAdvancedDurationInViewModel(item: Int) {
        mutableAdvancedDuration.value = item
    }

    private val mutableIsAdvancedDurationBottomSheetVisible = MutableLiveData<Boolean>()
    val isAdvancedDurationBottomSheetVisible: LiveData<Boolean> get() = mutableIsAdvancedDurationBottomSheetVisible

    fun setIsAdvancedDurationBottomSheetVisible(item: Boolean) {
        mutableIsAdvancedDurationBottomSheetVisible.value = item
    }

    // duration
    private val mutableDuration = MutableLiveData<Int>()
    val duration: LiveData<Int> get() = mutableDuration

    fun setDurationInViewModel(item: Int) {
        mutableDuration.value = item
    }

    private val mutableIsDurationBottomSheetVisible = MutableLiveData<Boolean>()
    val isDurationBottomSheetVisible: LiveData<Boolean> get() = mutableIsDurationBottomSheetVisible

    fun setIsDurationBottomSheetVisible(item: Boolean) {
        mutableIsDurationBottomSheetVisible.value = item
    }

    // repeat
    private val mutableRepeat = MutableLiveData<Rep>()
    val repeat: LiveData<Rep> get() = mutableRepeat

    fun setRepeatInViewModel(item: Rep) {
        mutableRepeat.value = item
    }

    private val mutableIsRepeatBottomSheetVisible = MutableLiveData<Boolean>()
    val isRepeatBottomSheetVisible: LiveData<Boolean> get() = mutableIsRepeatBottomSheetVisible

    fun setIsRepeatBottomSheetVisible(item: Boolean) {
        mutableIsRepeatBottomSheetVisible.value = item
    }

}
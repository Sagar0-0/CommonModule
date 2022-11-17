package fit.asta.health.scheduler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.model.net.scheduler.Wk
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    // label
    private val mutableLabel = MutableLiveData<String>()
    val alarmLabel: LiveData<String> get() = mutableLabel

    fun setAlarmLabelInViewModel(item: String) {
        mutableLabel.value = item
    }

    private val mutableIsLabelBottomSheetVisible = MutableLiveData<Boolean>()
    val isLabelBottomSheetVisible: LiveData<Boolean> get() = mutableIsLabelBottomSheetVisible

    fun setIsLabelBottomSheetVisible(item: Boolean) {
        mutableIsLabelBottomSheetVisible.value = item
    }

    // description
    private val mutableDescription = MutableLiveData<String>()
    val alarmDescription: LiveData<String> get() = mutableDescription

    fun setAlarmDescriptionInViewModel(item: String) {
        mutableDescription.value = item
    }

    private val mutableIsDescriptionBottomSheetVisible = MutableLiveData<Boolean>()
    val isDescriptionBottomSheetVisible: LiveData<Boolean> get() = mutableIsDescriptionBottomSheetVisible

    fun setIsDescriptionBottomSheetVisible(item: Boolean) {
        mutableIsDescriptionBottomSheetVisible.value = item
    }

    // reminder mode
    private val mutableChoice = MutableLiveData<String>()
    val alarmRemainderChoice: LiveData<String> get() = mutableChoice

    fun setAlarmReminderChoice(item: String) {
        mutableChoice.value = item
    }

    private val mutableIsReminderModeBottomSheetVisible = MutableLiveData<Boolean>()
    val isReminderModeBottomSheetVisible: LiveData<Boolean> get() = mutableIsReminderModeBottomSheetVisible

    fun setIsReminderModeBottomSheetVisible(item: Boolean) {
        mutableIsReminderModeBottomSheetVisible.value = item
    }

    // vibration slider
    private val mutableVibration = MutableLiveData<String>()
    val alarmVibrationPoint: LiveData<String> get() = mutableVibration

    fun setVibrationPoint(item: String) {
        mutableVibration.value = item
    }

    private val mutableIsVibrationBottomSheetVisible = MutableLiveData<Boolean>()
    val isVibrationBottomSheetVisible: LiveData<Boolean> get() = mutableIsVibrationBottomSheetVisible

    fun setIsVibrationBottomSheetVisible(item: Boolean) {
        mutableIsVibrationBottomSheetVisible.value = item
    }

//    // ringtone
//    private val mutableRingtone = MutableLiveData<RingtoneItem>()
//    val alarmRingtone: LiveData<RingtoneItem> get() = mutableRingtone
//
//    fun setVibrationPoint(item: RingtoneItem) {
//        mutableRingtone.value = item
//    }

    // custom tag
    private val mutableCustomTagName = MutableLiveData<TagEntity>()
    val alarmCustomTagName: LiveData<TagEntity> get() = mutableCustomTagName

    fun setAlarmCustomTagInViewModel(item: TagEntity) {
        mutableCustomTagName.value = item
    }

    private val mutableIsCustomTagBottomSheetVisible = MutableLiveData<Boolean>()
    val isCustomTagBottomSheetVisible: LiveData<Boolean> get() = mutableIsCustomTagBottomSheetVisible

    fun setIsCustomTagBottomSheetVisible(item: Boolean) {
        mutableIsCustomTagBottomSheetVisible.value = item
    }

    // custom tag
    private val mutableWeek = MutableLiveData<Wk>()
    val alarmWeek: LiveData<Wk> get() = mutableWeek

    fun setAlarmWeekInViewModel(item: Wk) {
        mutableWeek.value = item
    }

    private val mutableIsWeekBottomSheetVisible = MutableLiveData<Boolean>()
    val isWeekBottomSheetVisible: LiveData<Boolean> get() = mutableIsWeekBottomSheetVisible

    fun setIsWeekBottomSheetVisible(item: Boolean) {
        mutableIsWeekBottomSheetVisible.value = item
    }

    private val mutableSelectedTag = MutableLiveData<TagEntity>()
    val selectedTag: LiveData<TagEntity> get() = mutableSelectedTag

    fun setSelectedTag(item: TagEntity) {
        mutableSelectedTag.value = item
    }

}